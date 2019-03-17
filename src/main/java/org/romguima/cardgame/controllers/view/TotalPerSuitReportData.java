package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.model.CardSuit;
import org.romguima.cardgame.model.Game;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

public class TotalPerSuitReportData implements ReportData<Game> {

    private List<SuitCards> cardsPerSuit;

    @Override
    public void fill(Game data) {
        cardsPerSuit = new ArrayList<>();

        stream(CardSuit.values()).forEach(suit -> {
            SuitCards suitCards = new SuitCards();
            suitCards.setCardSuit(suit);
            suitCards.setTotal(data.getCards().stream().filter(card -> card.getSuit() == suit).count());

            cardsPerSuit.add(suitCards);
        });
    }

    public List<SuitCards> getCardsPerSuit() {
        return cardsPerSuit;
    }
}
