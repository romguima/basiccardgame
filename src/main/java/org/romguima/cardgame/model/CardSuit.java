package org.romguima.cardgame.model;

public enum CardSuit {

    HEARTS("Hearts"),

    SPADES("Spades"),

    CLUBS("Clubs"),

    DIAMONDS("Diamonds");

    private String value;

    CardSuit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
