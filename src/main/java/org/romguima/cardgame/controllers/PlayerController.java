package org.romguima.cardgame.controllers;

import org.romguima.cardgame.controllers.view.PlayerDTO;
import org.romguima.cardgame.model.Player;
import org.romguima.cardgame.model.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Resource<PlayerDTO>> createPlayer(@RequestBody PlayerDTO player) {
        Player entity = new Player();
        player.updatePlayer(entity);

        Resource<PlayerDTO> result = PlayerDTO.from(playerService.create(entity));

        return created(PlayerDTO.getSelfLinkBuilder(result.getContent()).toUri()).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<PlayerDTO>> retrievePlayer(@PathVariable String id) {
        return ok(PlayerDTO.from(playerService.retrieve(id)));
    }

    @GetMapping
    public ResponseEntity<List<Resource<PlayerDTO>>> listPlayers() {
        List<Player> players = playerService.retrieveAll();

        return ok(players.stream().map(PlayerDTO::simplifiedFrom).collect(toList()));
    }

}
