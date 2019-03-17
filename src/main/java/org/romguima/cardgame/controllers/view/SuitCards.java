package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.model.CardSuit;

public class SuitCards {

    private CardSuit cardSuit;

    private String cardValue;

    private Long total;

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(CardSuit cardSuit) {
        this.cardSuit = cardSuit;
    }

    public String getCardValue() {
        return cardValue;
    }

    public void setCardValue(String cardValue) {
        this.cardValue = cardValue;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
