package org.romguima.cardgame.bdd.steps;

import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class GameStepDefinitions extends BaseStepDefinitions implements En {

    private static Object previousId;
    private static String previousGameName;
    private static String lastDeckId;
    private static String firstPlayerId;

    public GameStepDefinitions() {
        When("^A call to create a Game with name \"([^\"]*)\" is made$", (String name) -> {
            lastResponse = given().body("{ \"name\":  \""+ name + "\" }")
                                    .contentType(ContentType.JSON.withCharset("UTF-8"))
                                    .when().post("/games");

            previousGameName = name;
        });

        Then("^A new Game is created$", () -> {
            lastResponse.then().statusCode(HttpStatus.SC_CREATED);
            previousId = lastResponse.path("id");
        });

        And("^Its information is returned$", () ->
            lastResponse.then()
                    .body("id", is(previousId))
                    .body("name", is(previousGameName))
                    .body(LINKS_SELF_HREF_FIELD, endsWith("/games/" + previousId))
        );

        Given("^There is a previously created Game$", () -> assertThat(previousId).isNotNull());

        When("^A call to retrieve a Game is made$", () -> {
            lastResponse = when().get("/games/{id}", previousId);
            lastResponse.then().statusCode(HttpStatus.SC_OK);
        });

        When("^A call to delete that Game is made$", () -> {
            lastResponse = when().delete("/games/{id}", previousId);
            lastResponse.then().statusCode(HttpStatus.SC_OK);
        });

        Then("^It's not possible to retrieve it anymore$", () -> {
            lastResponse = when().get("/games/{id}", previousId);
            lastResponse.then().statusCode(HttpStatus.SC_NOT_FOUND);
        });

        Given("^A call to retrieve a Game with id \"([^\"]*)\" is made$", (String id) ->
                lastResponse = when().get("/games/{id}", id));

        Then("^A status code \"(\\d*)\" is returned$", (Integer statusCode) -> {
            assertThat(lastResponse).isNotNull();

            lastResponse.then().statusCode(statusCode);
        });

        When("^A call to add the Player with id \"([^\"]*)\" into this Game$", (String playerId) -> {
            lastResponse.then().statusCode(HttpStatus.SC_CREATED);
            previousId = lastResponse.path("id");

            lastResponse = given().patch("/games/{id}/players/{playerId}", previousId, playerId);
        });

        Then("^The game information contains the Player with id \"([^\"]*)\"$", (String playerId) -> {
            lastResponse.then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("players*.id", contains(playerId));
        });

        Given("^There is an previously created Game$", () -> {
            assertThat(lastResponse).isNotNull();
            assertThat(previousId).isNotNull();

            lastResponse = when().get("/games/{id}", previousId);
            lastResponse.then().statusCode(HttpStatus.SC_OK);
        });

        And("^There is a card Deck available$", () -> {
            Response response = when().post("/decks");

            response.then().statusCode(HttpStatus.SC_CREATED);

            lastDeckId = response.path("id");
            assertThat(lastDeckId).isNotBlank();
        });

        When("^A call to add a Dack into a Game is made$", () -> {
            lastResponse = when().patch("/games/{id}/deck/{deckId}", previousId, lastDeckId);
            lastResponse.then().statusCode(HttpStatus.SC_OK);
        });

        Then("^The Game's deck size goes to \"(\\d*)\"$", (Integer size) ->
                lastResponse.then().body("deckSize", is(size)));

        And("^That Game has a deck size of \"(\\d*)\" cards$", (Integer size) ->
                lastResponse.then().body("deckSize", is(size)));

        When("^A call to deal \"(\\d*)\" cards to the first Player is made$", (Integer quantity) -> {
            firstPlayerId = lastResponse.path("players[0].id");

            lastResponse = given().param("quantity", quantity)
                    .when().patch("/games/{id}/players/{playerId}/cards", previousId, firstPlayerId);

            lastResponse.then().statusCode(HttpStatus.SC_OK);
        });

        And("^That Game has at least \"(\\d*)\" Player$", (Integer noPlayers) -> {
            lastResponse.then().body("players.size()", greaterThanOrEqualTo(noPlayers));
        });

        And("^The Player has \"(\\d*)\" cards for that Game$", (Integer noCards) -> {
            Response response = when().get("/players/{id}", firstPlayerId);
            response.then().statusCode(HttpStatus.SC_OK)
                    .body("games[0].gameId", is(previousId))
                    .body("games[0].cards.size()", is(noCards));
        });

        When("^A call to remove the first Player from that Game is made$", () -> {
            lastResponse = when().delete("/games/{id}/players/{playerId}", previousId, firstPlayerId);
            lastResponse.then().statusCode(HttpStatus.SC_OK);
        });

        Then("^The Game information does not include that Player anymore$", () -> {
            lastResponse.then().body("players", nullValue())
                    .body("deckSize", is(52));
        });

    }
}
