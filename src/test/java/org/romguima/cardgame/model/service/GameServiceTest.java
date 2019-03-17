package org.romguima.cardgame.model.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.romguima.cardgame.model.*;
import org.romguima.cardgame.model.repository.GameRepository;
import org.romguima.cardgame.model.repository.LockingGameRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private LockingGameRepository lockingGameRepository;

    @Mock
    private DeckService deckService;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private GameService gameService;

    @Test
    public void shouldReturnGameRetrievedByRepository() {
        final Game expected = new Game();

        given(gameRepository.findById(expected.getId())).willReturn(Optional.of(expected));

        Game result = gameService.retrieve(expected.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expected.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenRepositoryFindByIdReturnsEmpty() {
        given(gameRepository.findById("1")).willReturn(Optional.empty());

        gameService.retrieve("1");
    }

    @Test
    public void shouldCreateGame() {
        final String expectedGameName = "Test";
        Game game = new Game();
        game.setName(expectedGameName);

        given(gameRepository.save(any(Game.class))).will(returnsFirstArg());

        Game result = gameService.create(game);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(expectedGameName);
    }

    @Test
    public void shouldAddDeckToGame() {
        final List<Card> expectedCards =
                asList(Card.with(CardSuit.HEARTS, CardValue.KING), Card.with(CardSuit.HEARTS, CardValue.QUEEN));

        Deck deck = new Deck();
        deck.setCards(expectedCards);

        Game game = new Game();

        given(lockingGameRepository.findById(game.getId())).willReturn(Optional.of(game));
        given(deckService.retrieve(deck.getId())).willReturn(deck);
        given(gameRepository.save(game)).willReturn(game);

        Game result = gameService.addDeck(game.getId(), deck.getId());

        assertThat(result).isNotNull();
        assertThat(result.getCards()).containsExactlyInAnyOrder(expectedCards.toArray(new Card[0]));

        verify(deckService).delete(deck.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenGameNotExistsOnAddingDeck() {
        Deck deck = new Deck();

        given(lockingGameRepository.findById("nonexistent")).willReturn(Optional.empty());

        gameService.addDeck("nonexistent", deck.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeckNotExistsOnAddingDeck() {
        Game game = new Game();

        given(lockingGameRepository.findById(game.getId())).willReturn(Optional.of(game));
        given(deckService.retrieve("nonexistent")).willThrow(new EntityNotFoundException());

        gameService.addDeck(game.getId(),"nonexistent");
    }

    @Test
    public void shouldDeleteGame() {
        Game game = new Game();

        given(gameRepository.findById(game.getId())).willReturn(Optional.of(game));

        gameService.delete(game.getId());

        verify(gameRepository).delete(game);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenTryingToDeleteInexistentGame() {
        given(gameRepository.findById("invalid")).willReturn(Optional.empty());

        gameService.delete("invalid");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenAddingPlayerThatDoesNotExistsIntoGame() {
        Game game = new Game();

        given(gameRepository.findById(game.getId())).willReturn(Optional.of(game));
        given(playerService.retrieve("invalid")).willThrow(new EntityNotFoundException());

        gameService.addPlayer(game.getId(), "invalid");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenAddingPlayerIntoGameThatDoesNotExists() {
        Player player = new Player();

        given(gameRepository.findById("invalid")).willReturn(Optional.empty());

        gameService.addPlayer("invalid", player.getId());
    }

    @Test
    public void shouldReturnGameWithPlayer() {
        Game game = new Game();
        Player player = new Player();

        given(gameRepository.findById(game.getId())).willReturn(Optional.of(game));
        given(gameRepository.save(game)).willReturn(game);
        given(playerService.retrieve(player.getId())).willReturn(player);

        Game result = gameService.addPlayer(game.getId(), player.getId());

        assertThat(result).isNotNull();
        assertThat(result.getPlayers()).hasSize(1);
        assertThat(result.getPlayers().get(0).getPlayer().getId()).isEqualTo(player.getId());

        verify(gameRepository).save(game);
    }

    @Test
    public void shouldNotAddPlayerToGameIfAlreadyIn() {
        Game game = new Game();
        Player player = new Player();
        GamePlayer gamePlayer = GamePlayer.with(game, player);
        game.setPlayers(singletonList(gamePlayer));

        given(gameRepository.findById(game.getId())).willReturn(Optional.of(game));
        given(playerService.retrieve(player.getId())).willReturn(player);

        Game result = gameService.addPlayer(game.getId(), player.getId());

        assertThat(result).isNotNull();
        assertThat(result.getPlayers()).hasSize(1);
        assertThat(result.getPlayers().get(0).getPlayer().getId()).isEqualTo(player.getId());

        verify(gameRepository, times(0)).save(game);
    }
}