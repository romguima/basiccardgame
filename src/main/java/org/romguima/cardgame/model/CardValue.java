package org.romguima.cardgame.model;

public enum CardValue {

    ACE("A", 1),

    REGULAR_2("2", 2),

    REGULAR_3("3", 3),

    REGULAR_4("4", 4),

    REGULAR_5("5", 5),

    REGULAR_6("6", 6),

    REGULAR_7("7", 7),

    REGULAR_8("8", 8),

    REGULAR_9("9", 9),

    REGULAR_10("10", 10),

    JACK("J", 11),

    QUEEN("Q", 12),

    KING("K", 13);

    private String faceValue;

    private int cardValue;

    CardValue(String faceValue, int cardValue) {
        this.faceValue = faceValue;
        this.cardValue = cardValue;
    }

    public int getCardValue() {
        return cardValue;
    }

    public String getFaceValue() {
        return faceValue;
    }
}
