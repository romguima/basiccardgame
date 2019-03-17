package org.romguima.cardgame.bdd.steps;

import cucumber.api.java8.En;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

public class PlayerStepDefinitions extends BaseStepDefinitions implements En {

    private static String previousName;

    public PlayerStepDefinitions() {
        When("^A call to create a Player with name \"([^\"]*)\" is made$", (String name) -> {
            lastResponse =
                    given().body("{ \"name\": \"" + name + "\" }")
                            .contentType(ContentType.JSON.withCharset("UTF-8"))
                            .post("/players");

            previousName = name;
        });

        Then("^Player's information is returned with code \"(\\d*)\"$", (Integer statusCode) -> {
            assertThat(lastResponse).isNotNull();
            lastResponse.then()
                    .statusCode(statusCode)
                    .body("id", is((String) lastResponse.path("id")))
                    .body("name", is(previousName))
                    .body(LINKS_SELF_HREF_FIELD, endsWith("/players/" + lastResponse.path("id")));
        });

        When("^A call to retrieve the previously created Player is made$", () -> {
            assertThat(lastResponse).isNotNull();
            String previousId = lastResponse.path("id");

            lastResponse = when().get("/players/{id}", previousId);
        });

        Given("^A call to create a Player with name \"([^\"]*)\" and id \"([^\"]*)\" is made$",
                (String name, String id) -> {
                    lastResponse =
                            given().body("{ \"id\": \"" + id + "\", \"name\": \"" + name + "\" }")
                                    .contentType(ContentType.JSON.withCharset("UTF-8"))
                                    .post("/players");

                    lastResponse.then().statusCode(HttpStatus.SC_CREATED);
                    previousName = name;
        });
    }
}
