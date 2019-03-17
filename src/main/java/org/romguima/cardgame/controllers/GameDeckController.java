package org.romguima.cardgame.controllers;

import org.romguima.cardgame.controllers.view.DeckReport;
import org.romguima.cardgame.controllers.view.DeckView;
import org.romguima.cardgame.controllers.view.GameDTO;
import org.romguima.cardgame.model.Game;
import org.romguima.cardgame.model.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/games/{id}/deck")
public class GameDeckController {

    private final GameService gameService;

    @Autowired
    public GameDeckController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<DeckReport> deckQuery(@PathVariable String id,
                                                @RequestParam(defaultValue = "totalPerSuit") DeckView view)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Game game = gameService.retrieve(id);

        DeckReport deckReport = DeckReport.from(game, view.getHandler().getConstructor().newInstance());

        return ok(deckReport);
    }

    @PatchMapping
    public ResponseEntity<Void> shuffleDeck(@PathVariable String id) {
        gameService.shuffleDeck(id);

        return ok(null);
    }

    @PatchMapping("/{deckId}")
    public ResponseEntity<Resource<GameDTO>> addDeck(@PathVariable String id, @PathVariable String deckId) {
        Game game = gameService.addDeck(id, deckId);

        return ok(GameDTO.from(game));
    }

}
