package org.romguima.cardgame.model.service;

import org.romguima.cardgame.model.Card;
import org.romguima.cardgame.model.CardSuit;
import org.romguima.cardgame.model.CardValue;
import org.romguima.cardgame.model.Deck;
import org.romguima.cardgame.model.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class DeckService {

    private final DeckRepository deckRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Deck create() {
        Deck deck = new Deck();

        deck.setCards(generateCards());

        return deckRepository.save(deck);
    }

    private List<Card> generateCards() {
        return stream(CardSuit.values())
                .flatMap(suit -> stream(CardValue.values())
                        .map(value -> Card.with(suit, value)))
                .collect(toList());
    }

    public Deck retrieve(String id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Deck from id: " + id));
    }

    void delete(String id) {
        deckRepository.deleteById(id);
    }
}
