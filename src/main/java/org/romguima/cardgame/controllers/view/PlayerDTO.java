package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.controllers.PlayerController;
import org.romguima.cardgame.model.Player;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PlayerDTO {

    private String id;

    private String name;

    private List<GameCardsDTO> games;

    public static Resource<PlayerDTO> from(Player player) {
        PlayerDTO result = new PlayerDTO();
        result.id = player.getId();
        result.name = player.getName();

        if (player.getGames() != null) {
            result.games = player.getGames().stream().map(GameCardsDTO::from).collect(toList());
        }

        return asResource(result);
    }

    public static Resource<PlayerDTO> simplifiedFrom(Player player) {
        PlayerDTO result = new PlayerDTO();
        result.id = player.getId();
        result.name = player.getName();
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

    public List<GameCardsDTO> getGames() {
        return games;
    }

    public void updatePlayer(Player player) {
        if (this.id != null) {
            player.setId(this.id);
        }
        player.setName(this.name);
    }

    public static Resource<PlayerDTO> asResource(PlayerDTO player) {
        return new Resource<>(player, getSelfLinkBuilder(player).withSelfRel());
    }

    public static ControllerLinkBuilder getSelfLinkBuilder(PlayerDTO result) {
        return linkTo(methodOn(PlayerController.class, result.getId()).retrievePlayer(result.getId()));
    }

}
