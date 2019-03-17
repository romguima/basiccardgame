package org.romguima.cardgame.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GAME_PLAYER")
public class GamePlayer {

    @EmbeddedId
    private GamePlayerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gameId")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    private Player player;

    @OneToMany(targetEntity = Card.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
    private List<Card> cards;

    public static GamePlayer with(Game game, Player player) {
        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setGame(game);
        gamePlayer.setPlayer(player);
        gamePlayer.setId(GamePlayerId.with(game.getId(), player.getId()));
        return gamePlayer;
    }

    public GamePlayerId getId() {
        return id;
    }

    public void setId(GamePlayerId id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GamePlayer)) return false;
        GamePlayer that = (GamePlayer) o;
        return Objects.equals(game, that.game) &&
                Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, player);
    }
}
