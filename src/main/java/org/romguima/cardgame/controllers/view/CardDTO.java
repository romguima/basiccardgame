package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.model.Card;

public class CardDTO {

    private String id;

    private String suit;

    private String value;

    public static CardDTO from(Card card) {
        CardDTO result = new CardDTO();
        result.id = card.getId();
        result.suit = card.getSuit() != null ? card.getSuit().getValue() : null;
        result.value = card.getValue() != null ? card.getValue().getFaceValue() : null;

        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
