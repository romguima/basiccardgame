package org.romguima.cardgame.model.service;

import com.google.common.collect.Lists;
import org.romguima.cardgame.model.Player;
import org.romguima.cardgame.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player create(Player player) {
        return playerRepository.save(player);
    }

    public Player retrieve(String id) {
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Player from id: " + id));
    }

    public List<Player> retrieveAll() {
        return Lists.newArrayList(playerRepository.findAll());
    }
}
