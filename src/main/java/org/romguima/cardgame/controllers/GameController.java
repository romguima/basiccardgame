package org.romguima.cardgame.controllers;

import org.romguima.cardgame.controllers.view.GameDTO;
import org.romguima.cardgame.model.Game;
import org.romguima.cardgame.model.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Resource<GameDTO>> createGame(@RequestBody GameDTO game) {
        Game result = gameService.create(game.toEntity());

        Resource<GameDTO> resource = GameDTO.from(result);
        return created(GameDTO.getGameSelfLinkBuilder(resource.getContent()).toUri()).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<GameDTO>> retrieveGame(@PathVariable String id) {
        Game result = gameService.retrieve(id);

        return ok(GameDTO.from(result));
    }

    @GetMapping
    public ResponseEntity<List<Resource<GameDTO>>> listGames() {
        List<Game> games = gameService.retrieveAll();

        return ok(games.stream().map(GameDTO::idOnlyFrom).collect(toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {
        gameService.delete(id);

        return ok(null);
    }

}
