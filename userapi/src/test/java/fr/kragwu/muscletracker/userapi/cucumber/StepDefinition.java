package fr.kragwu.muscletracker.userapi.cucumber;

import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;
import org.hamcrest.Matchers;
import org.json.JSONObject;

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
    public void a_new_user() {
        login = "test";
        password = "password";
        registrationDTO = new RegistrationDTO(login, password);
    }

    @Given("A user already registered")
    public void aUserAlreadyRegistered() {
        a_new_user();
    }

    @Given("A user already login")
    public void aUserAlreadyLogin() {
        a_new_user();
        try_connect_first_time();
        receivedSucceedLogin();
    }

    @When("Try to connect")
    public void try_connect_first_time() {
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

    @Then("received error message unknown user")
    public void received_error_unknown_user() {
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
}
