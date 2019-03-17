package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.controllers.GamePlayerController;
import org.romguima.cardgame.model.Card;
import org.romguima.cardgame.model.CardValue;
import org.romguima.cardgame.model.GamePlayer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class Players {

    private Resource<PlayerDTO> player;

    private int totalValue;

    public static Players from(GamePlayer gamePlayer) {
        Players result = new Players();

        result.player = PlayerDTO.simplifiedFrom(gamePlayer.getPlayer());
        result.totalValue = 0;

        if (gamePlayer.getCards() != null) {
            result.totalValue =
                    gamePlayer.getCards().stream().map(Card::getValue).mapToInt(CardValue::getCardValue).sum();
        }

        return result;
    }

    public Resource<PlayerDTO> getPlayer() {
        return player;
    }

    public void setPlayer(Resource<PlayerDTO> player) {
        this.player = player;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public static Resources<List<Players>> asResources(List<Players> dtos, String gameId) {
        return new Resources(dtos,
                GameDTO.getGameSelfLinkBuilder(gameId).withRel("parent"),
                getGamePlayersSelfLinkBuilder(gameId).withSelfRel());
    }

    public static ControllerLinkBuilder getGamePlayersSelfLinkBuilder(String id) {
        return linkTo(methodOn(GamePlayerController.class, id).listPlayers(id));
    }


}
