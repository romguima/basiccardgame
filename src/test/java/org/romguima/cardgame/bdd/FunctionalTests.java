package org.romguima.cardgame.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/features", plugin = {"pretty"}, strict = true, tags = {"~@wip"})
public class FunctionalTests {

}
