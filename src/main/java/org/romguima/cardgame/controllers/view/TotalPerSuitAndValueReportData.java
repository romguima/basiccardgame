package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.model.CardSuit;
import org.romguima.cardgame.model.CardValue;
import org.romguima.cardgame.model.Game;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

public class TotalPerSuitAndValueReportData implements ReportData<Game> {

    private List<SuitCards> cardsPerSuitAndValue;

    @Override
    public void fill(Game data) {
        cardsPerSuitAndValue = new ArrayList<>();

        stream(CardSuit.values()).forEach(suit ->
            stream(CardValue.values()).forEach(value -> {
                SuitCards suitCards = new SuitCards();
                suitCards.setCardSuit(suit);
                suitCards.setCardValue(value.getFaceValue());
                suitCards.setTotal(data.getCards().stream()
                        .filter(card -> card.getSuit() == suit && card.getValue() == value).count());

                cardsPerSuitAndValue.add(suitCards);
            }));
    }

    public List<SuitCards> getCardsPerSuitAndValue() {
        return cardsPerSuitAndValue;
    }
}
