package org.romguima.cardgame.model.repository;

import org.romguima.cardgame.model.Deck;
import org.springframework.data.repository.CrudRepository;

public interface DeckRepository extends CrudRepository<Deck, String> {

}
