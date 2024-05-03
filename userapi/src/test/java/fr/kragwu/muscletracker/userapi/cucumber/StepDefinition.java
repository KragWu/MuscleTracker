package fr.kragwu.muscletracker.userapi.cucumber;

import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;

import fr.kragwu.muscletracker.userapi.controllers.dto.RegistrationDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class StepDefinition {

    String login;
    String password;
    RegistrationDTO registrationDTO;
    SessionDTO sessionDTO;
    Response response;

    @Given("A new User")
    public void aNewUser() {
        login = "test";
        password = "password";
        registrationDTO = new RegistrationDTO(login, password);
    }

    @Given("A user already registered")
    public void aUserAlreadyRegistered() {
        aNewUser();
    }

    @Given("A user already login")
    public void aUserAlreadyLogin() {
        aNewUser();
        tryConnectFirstTime();
        receivedSucceedLogin();
    }

    @When("Try to connect")
    public void tryConnectFirstTime() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .post("/login");
    }

    @When("Try to register")
    public void registerFirstTime() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .post("/register");
    }

    @When("Try to logout")
    public void tryToLogout() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(sessionDTO)
                .when()
                .post("/logout");
    }

    @When("Try to authorize")
    public void tryToAuthorize() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(sessionDTO)
                .when()
                .post("/authorize");
    }

    @Then("received error message unknown user")
    public void receivedErrorUnknownUser() {
        assertEquals(400, response.statusCode());
        assertEquals("", response.getBody().asString());
    }

    @Then("received succeed register")
    public void receivedSucceedRegister() {
        assertEquals(201, response.statusCode());
        assertEquals("OK", response.getBody().asString());
    }

    @Then("received succeed login")
    public void receivedSucceedLogin() {
        assertEquals(200, response.statusCode());
        assertNotEquals("", response.getBody().asString());
        sessionDTO = new SessionDTO(
                response.jsonPath().get("id") == null ? null : response.jsonPath().get("id"),
                response.jsonPath().get("idUser") == null ? null : response.jsonPath().get("idUser"),
                response.jsonPath().get("loginDateTime") == null ? null : LocalDateTime.parse(response.jsonPath().get("loginDateTime")),
                response.jsonPath().get("logoutDateTime") == null ? null : LocalDateTime.parse(response.jsonPath().get("logoutDateTime")),
                response.jsonPath().get("token") == null ? null : response.jsonPath().get("token"));
    }


    @Then("received failed register")
    public void receivedFailedRegister() {
        assertEquals(400, response.statusCode());
        assertEquals("KO", response.getBody().asString());
    }

    @Then("received succeed logout")
    public void receivedSucceedLogout() {
        assertEquals(200, response.statusCode());
        assertEquals("OK", response.getBody().asString());
    }

    @Then("received succeed authorization")
    public void receivedSucceedAuthorization() {
        assertEquals(200, response.statusCode());
        assertEquals("OK", response.getBody().asString());
    }

    @Then("received failed authorization")
    public void receivedFailedAuthorization() {
        assertEquals(401, response.statusCode());
        assertEquals("Unauthorized", response.getBody().asString());
    }
}
