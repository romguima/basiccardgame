package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.controllers.GameController;
import org.romguima.cardgame.model.Game;
import org.romguima.cardgame.model.GamePlayer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class GameDTO {

    private String id;

    private String name;

    private List<Resource<PlayerDTO>> players;

    private Integer deckSize;

    public static Resource<GameDTO> from(Game game) {
        GameDTO result = new GameDTO();

        result.id = game.getId();
        result.name = game.getName();

        if (game.getCards() != null) {
            result.deckSize = game.getCards().size();
        } else {
            result.deckSize = 0;
        }

        List<GamePlayer> gamePlayers = game.getPlayers();

        if (gamePlayers != null && !gamePlayers.isEmpty()) {
            result.players = gamePlayers.stream()
                    .map(gamePlayer -> PlayerDTO.simplifiedFrom(gamePlayer.getPlayer()))
                    .collect(toList());
        }

        return asResource(result);
    }

    public static Resource<GameDTO> idOnlyFrom(Game game) {
        GameDTO result = new GameDTO();

        result.id = game.getId();

        return asResource(result);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeckSize() {
        return deckSize;
    }

    public List<Resource<PlayerDTO>> getPlayers() {
        return players;
    }

    public void setPlayers(List<Resource<PlayerDTO>> players) {
        this.players = players;
    }

    public void update(Game game) {
        game.setName(this.name);
    }

    public Game toEntity() {
        Game game = new Game();
        game.setName(this.name);

        if (this.id != null) {
            game.setId(this.id);
        }

        return game;
    }

    public static Resource<GameDTO> asResource(GameDTO game) {
        return new Resource<>(game, getGameSelfLinkBuilder(game).withSelfRel());
    }

    public static ControllerLinkBuilder getGameSelfLinkBuilder(GameDTO result) {
        return linkTo(methodOn(GameController.class, result.getId()).retrieveGame(result.getId()));
    }

    public static ControllerLinkBuilder getGameSelfLinkBuilder(String id) {
        return linkTo(methodOn(GameController.class, id).retrieveGame(id));
    }
}
