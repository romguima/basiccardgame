package org.romguima.cardgame.model.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.romguima.cardgame.model.Card;
import org.romguima.cardgame.model.CardSuit;
import org.romguima.cardgame.model.CardValue;
import org.romguima.cardgame.model.Deck;
import org.romguima.cardgame.model.repository.DeckRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeckServiceTest {

    private int EXPECTED_DECK_SIZE = CardValue.values().length * CardSuit.values().length;

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private DeckService deckService;

    @Test
    public void shouldCreateDeckWithAllCards() {
        given(deckRepository.save(any(Deck.class))).will(returnsFirstArg());

        Deck deck = deckService.create();

        assertThat(deck).isNotNull();
        assertThat(deck.getCards()).hasSize(EXPECTED_DECK_SIZE);

        stream(getExpectedCardsInDeck()).forEach(card ->
                assertThat(deck.getCards().stream()
                        .filter(otherCard -> otherCard.getSuit() == card.getSuit()
                                && otherCard.getValue() == card.getValue())
                        .count())
                        .isEqualTo(1L));
    }

    @Test
    public void shouldReturnDeckReturnedByRepository() {
        final Deck expectedDeck = new Deck();

        given(deckRepository.findById(expectedDeck.getId())).willReturn(Optional.of(expectedDeck));

        Deck result = deckService.retrieve(expectedDeck.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expectedDeck.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenRepositoryFindByIdReturnsEmpty() {
        given(deckRepository.findById(anyString())).willReturn(Optional.empty());

        deckService.retrieve("1");
    }

    @Test
    public void shouldCallRepositoryDelete() {
        deckService.delete("expectedId");

        verify(deckRepository).deleteById("expectedId");
    }

    private Card[] getExpectedCardsInDeck() {
        return stream(CardSuit.values())
                .flatMap(suit ->
                        stream(CardValue.values()).map(value -> Card.with(suit, value)))
                .toArray(Card[]::new);
    }
}