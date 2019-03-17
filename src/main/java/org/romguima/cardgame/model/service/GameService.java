package org.romguima.cardgame.model.service;

import com.google.common.collect.Lists;
import org.romguima.cardgame.model.*;
import org.romguima.cardgame.model.repository.GameRepository;
import org.romguima.cardgame.model.repository.LockingGameRepository;
import org.romguima.cardgame.model.service.exception.InvalidDealCardsQuantityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class GameService {

    private final GameRepository repository;

    private final LockingGameRepository lockingRepository;

    private final DeckService deckService;

    private final PlayerService playerService;

    @Autowired
    public GameService(GameRepository repository,
                       LockingGameRepository lockingRepository,
                       DeckService deckService,
                       PlayerService playerService) {
        this.repository = repository;
        this.lockingRepository = lockingRepository;
        this.deckService = deckService;
        this.playerService = playerService;
    }

    public Game create(Game game) {
        return repository.save(game);
    }

    public Game retrieve(String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Game from id: " + id));
    }

    private Game retrieveLocking(String id) {
        return lockingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Game from id: " + id));
    }

    public Game addDeck(String id, String deckId) {
        Game game = retrieveLocking(id);
        Deck deck = deckService.retrieve(deckId);

        if (game.getCards() == null) {
            game.setCards(new ArrayList<>());
        }

        game.getCards().addAll(deck.getCards());
        game.setCards(getShuffledCards(game.getCards()));

        Game result = repository.save(game);

        deckService.delete(deckId);

        return result;
    }

    private List<Card> getShuffledCards(List<Card> cards) {
        int size = cards.size();
        List<Integer> availableOrders = IntStream.range(0, size).boxed().collect(Collectors.toList());

        return cards.stream()
                        .peek(card -> card.setPosition(nextOrder(availableOrders, size)))
                        .sorted(comparing(Card::getPosition)).collect(Collectors.toList());
    }

    private Integer nextOrder(List<Integer> availableOrders, int maxValue) {
        Integer nextInt;

        if (availableOrders.size() == 1) {
            nextInt = availableOrders.get(0);
            availableOrders.clear();
        } else {
            do {
                nextInt = new Random().nextInt(maxValue);
            } while (!availableOrders.remove(nextInt));
        }

        return nextInt;
    }

    public void shuffleDeck(String id) {
        Game game = retrieveLocking(id);

        game.setCards(getShuffledCards(game.getCards()));

        repository.save(game);
    }

    public void delete(String id) {
        Game game = retrieve(id);

        repository.delete(game);
    }

    public Game addPlayer(String id, String playerId) {
        Game game = retrieve(id);
        Player player = playerService.retrieve(playerId);
        GamePlayer gamePlayer = GamePlayer.with(game, player);

        Game result = game;

        if (game.getPlayers() == null) {
            game.setPlayers(new ArrayList<>());
        }

        if (!game.getPlayers().contains(gamePlayer)) {
            game.getPlayers().add(gamePlayer);
            result = repository.save(game);
        }

        return result;
    }

    public Game removePlayer(String id, String playerId) {
        Game game = retrieveLocking(id);

        GamePlayer player = getGamePlayer(id, playerId, game.getPlayers());

        if (player.getCards() != null) {
            game.getCards().addAll(player.getCards());
            game.setCards(getShuffledCards(game.getCards()));
        }

        game.getPlayers().remove(player);

        return repository.save(game);
    }

    private GamePlayer getGamePlayer(String id, String playerId, List<GamePlayer> gamePlayers) {
        if (gamePlayers != null && !gamePlayers.isEmpty()) {
            return gamePlayers.stream()
                    .filter(gamePlayer -> gamePlayer.getPlayer().getId().equals(playerId))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Player from id " + playerId + " on game " + id));
        }

        throw new EntityNotFoundException("Player from id " + playerId + " on game " + id);
    }

    public Game dealCards(String id, String playerId, int quantity) {
        Game game = retrieveLocking(id);

        GamePlayer gamePlayer = getGamePlayer(id, playerId, game.getPlayers());

        if (gamePlayer.getCards() == null) {
            gamePlayer.setCards(new ArrayList<>());
        }

        List<Card> cardsFromDeck = getCardsFrom(quantity, game.getCards());
        gamePlayer.getCards().addAll(cardsFromDeck);

        return repository.save(game);
    }

    private List<Card> getCardsFrom(int quantity, List<Card> deck) {
        if (quantity < 1) {
            throw new InvalidDealCardsQuantityException(quantity);
        }

        List<Card> result = new ArrayList<>();
        result.addAll(deck.subList(0, Math.min(quantity, deck.size())));

        deck.removeAll(result);

        return result;
    }

    public List<Game> retrieveAll() {
        return Lists.newArrayList(repository.findAll());
    }
}
