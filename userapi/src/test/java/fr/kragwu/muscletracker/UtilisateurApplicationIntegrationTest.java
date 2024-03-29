package fr.kragwu.muscletracker;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import fr.kragwu.muscletracker.userapi.UtilisateurApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@SpringBootTest(
    classes = { UtilisateurApplication.class,
        UtilisateurApplicationIntegrationTest.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@CucumberOptions(strict = true, plugin = {"pretty", "html:target/cucumber"}, features = {"src/test/resources/features"})
public class UtilisateurApplicationIntegrationTest { }
