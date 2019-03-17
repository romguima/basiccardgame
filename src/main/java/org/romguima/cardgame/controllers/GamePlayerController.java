package org.romguima.cardgame.controllers;

import org.romguima.cardgame.controllers.view.GameDTO;
import org.romguima.cardgame.controllers.view.Players;
import org.romguima.cardgame.model.Game;
import org.romguima.cardgame.model.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/games/{id}/players")
public class GamePlayerController {

    private final GameService gameService;

    @Autowired
    public GamePlayerController(GameService gameService) {
        this.gameService = gameService;
    }

    @PatchMapping("/{playerId}/cards")
    public ResponseEntity<Resource<GameDTO>> dealCards(@PathVariable String id,
                                                    @PathVariable String playerId,
                                                    @RequestParam int quantity) {
        Game game = gameService.dealCards(id, playerId, quantity);

        return ok(GameDTO.from(game));
    }

    @PatchMapping("/{playerId}")
    public ResponseEntity<Resource<GameDTO>> addPlayer(@PathVariable String id, @PathVariable String playerId) {
        Game game = gameService.addPlayer(id, playerId);

        return ok(GameDTO.from(game));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Resource<GameDTO>> removePlayer(@PathVariable String id, @PathVariable String playerId) {
        Game game = gameService.removePlayer(id, playerId);

        return ok(GameDTO.from(game));
    }

    @GetMapping
    public ResponseEntity<Resources<List<Players>>> listPlayers(@PathVariable String id) {
        Game game = gameService.retrieve(id);

        List<Players> result = new ArrayList<>();

        if (game.getPlayers() != null) {
            result = game.getPlayers().stream()
                        .map(Players::from)
                        .sorted(Comparator.comparingInt(Players::getTotalValue).reversed())
                        .collect(Collectors.toList());
        }

        return ok(Players.asResources(result, id));
    }

}
