package org.romguima.cardgame.model.service.exception;

public class InvalidDealCardsQuantityException extends RuntimeException {
    public InvalidDealCardsQuantityException(int quantity) {
        super("Invalid deal cards quantity: " + quantity);
    }
}
