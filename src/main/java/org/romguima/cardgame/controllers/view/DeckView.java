package org.romguima.cardgame.controllers.view;

import static java.util.Arrays.stream;

public enum DeckView {

    TOTAL_PER_SUIT(TotalPerSuitReportData.class, "totalPerSuit"),

    TOTAL_PER_SUIT_AND_VALUE(TotalPerSuitAndValueReportData.class, "totalPerSuitAndValue");

    private Class<? extends ReportData> handler;

    private String name;

    DeckView(Class<? extends ReportData> handler, String name) {
        this.handler = handler;
        this.name = name;
    }

    public Class<? extends ReportData> getHandler() {
        return handler;
    }

    public String getName() {
        return name;
    }

    public static DeckView fromString(String value) {
        return stream(values()).filter(deckView -> deckView.name.equals(value)).findFirst().orElse(null);
    }
}
