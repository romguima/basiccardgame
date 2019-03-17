package org.romguima.cardgame.bdd.steps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.romguima.cardgame.CardGameApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;

@ContextConfiguration
@SpringBootTest(classes = CardGameApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseStepDefinitions {

    static final String LINKS_SELF_HREF_FIELD = "_links.self.href";

    static Response lastResponse;

    @LocalServerPort
    int localServerPort = 8080;

    @PostConstruct
    public void setUp() {
        RestAssured.port = localServerPort;
    }

}
