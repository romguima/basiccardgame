package org.romguima.cardgame.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GamePlayerId implements Serializable {

    private String gameId;

    private String playerId;

    public GamePlayerId() {
    }

    public GamePlayerId(String gameId, String playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public static GamePlayerId with(String id, String playerId) {
        GamePlayerId gamePlayerId = new GamePlayerId();
        gamePlayerId.setGameId(id);
        gamePlayerId.setPlayerId(playerId);
        return gamePlayerId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GamePlayerId)) return false;
        GamePlayerId that = (GamePlayerId) o;
        return Objects.equals(gameId, that.gameId) &&
                Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerId);
    }
}
