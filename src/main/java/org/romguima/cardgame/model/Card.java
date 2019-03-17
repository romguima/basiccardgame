package org.romguima.cardgame.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Card {

    @Id
    private String id = UUID.randomUUID().toString();

    private Integer position;

    @Enumerated(EnumType.STRING)
    private CardSuit suit;

    @Enumerated(EnumType.STRING)
    private CardValue value;

    public static Card with(CardSuit suit, CardValue value) {
        Card card = new Card();
        card.suit = suit;
        card.value = value;

        return card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public CardValue getValue() {
        return value;
    }

    public void setValue(CardValue value) {
        this.value = value;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
