package org.romguima.cardgame.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Deck {

    @Id
    private String id = UUID.randomUUID().toString();

    @OneToMany(targetEntity = Card.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Card> cards;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
