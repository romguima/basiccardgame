package org.romguima.cardgame.model.repository;

import org.romguima.cardgame.model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, String> {

}
