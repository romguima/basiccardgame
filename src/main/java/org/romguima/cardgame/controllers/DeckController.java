package org.romguima.cardgame.controllers;

import org.romguima.cardgame.model.Deck;
import org.romguima.cardgame.model.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @PostMapping
    public ResponseEntity<Resource<Deck>> createDeck() {
        Deck result = deckService.create();

        return created(getSelfLinkBuilder(result).toUri()).body(getResource(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Deck>> retrieveDeck(@PathVariable String id) {
        Deck result = deckService.retrieve(id);

        return ok(getResource(result));
    }

    private Resource<Deck> getResource(Deck deck) {
        return new Resource<>(deck, getSelfLinkBuilder(deck).withSelfRel());
    }

    private ControllerLinkBuilder getSelfLinkBuilder(Deck deck) {
        return linkTo(methodOn(DeckController.class, deck.getId()).retrieveDeck(deck.getId()));
    }

}
