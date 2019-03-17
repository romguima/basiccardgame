package org.romguima.cardgame.model.repository;

import org.romguima.cardgame.model.Game;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface LockingGameRepository extends CrudRepository<Game, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    Optional<Game> findById(String s);
}
