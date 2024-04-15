package fr.kragwu.muscletracker.userapi.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        glue = {"fr.kragwu.muscletracker.userapi.cucumber"})
public class CucumberIntegrationTest {

}
