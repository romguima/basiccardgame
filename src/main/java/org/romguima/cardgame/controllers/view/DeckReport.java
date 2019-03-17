package org.romguima.cardgame.controllers.view;

import org.romguima.cardgame.model.Game;
import org.springframework.hateoas.Resource;

public class DeckReport {

    private Resource<GameDTO> game;

    private ReportData<Game> details;

    public Resource<GameDTO> getGame() {
        return game;
    }

    public void setGame(Resource<GameDTO> game) {
        this.game = game;
    }

    public ReportData getDetails() {
        return details;
    }

    public void setDetails(ReportData details) {
        this.details = details;
    }

    public static DeckReport from(Game game, ReportData<Game> reportType) {
        DeckReport result = new DeckReport();

        result.game = GameDTO.idOnlyFrom(game);
        result.details = reportType;

        result.details.fill(game);

        return result;
    }

}
