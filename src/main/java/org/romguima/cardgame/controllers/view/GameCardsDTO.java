package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.model.GamePlayer;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class GameCardsDTO {

    private String gameId;

    private List<CardDTO> cards;

    public static GameCardsDTO from(GamePlayer gamePlayer) {
        GameCardsDTO result = new GameCardsDTO();
        result.gameId = gamePlayer.getId().getGameId();

        if (gamePlayer.getCards() != null) {
            result.cards = gamePlayer.getCards().stream().map(CardDTO::from).collect(toList());
        }

        return result;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }
}
