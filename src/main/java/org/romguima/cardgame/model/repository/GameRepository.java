package org.romguima.cardgame.model.repository;

import org.romguima.cardgame.model.Game;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, String> {

}
