package org.romguima.cardgame.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Player {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    @OneToMany(mappedBy = "player", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private List<GamePlayer> games;

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

    public List<GamePlayer> getGames() {
        return games;
    }

    public void setGames(List<GamePlayer> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
