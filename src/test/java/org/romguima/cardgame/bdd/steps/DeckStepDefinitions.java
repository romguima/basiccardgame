package org.romguima.cardgame.bdd.steps;

import cucumber.api.java8.En;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

public class DeckStepDefinitions extends BaseStepDefinitions implements En {
    public DeckStepDefinitions() {
        When("^A call to create a Deck is made$", () -> lastResponse = when().post("/decks"));

        When("^A call to retrieve the previously created Deck is made$", () -> {
            assertThat(lastResponse).isNotNull();
            lastResponse = when().get("/decks/{id}", (String) lastResponse.path("id"));
        });

        Then("^Deck's information is returned with code \"(\\d*)\"$", (Integer statusCode) -> {
                assertThat(lastResponse).isNotNull();

                lastResponse.then()
                    .statusCode(statusCode)
                    .body("id", is((String) lastResponse.path("id")))
                    .body(LINKS_SELF_HREF_FIELD, endsWith("/decks/" + lastResponse.path("id")));
        });
    }
}
