package fr.kragwu.muscletracker.userapi.cucumber;

import fr.kragwu.muscletracker.userapi.controllers.dto.RegistrationDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class StepDefinition {

    String login;
    String password;
    RegistrationDTO registrationDTO;
    Response response;
    String sessionId;
    String sessionToken;

    @Given("A User")
    public void aUser(String strLogin, String strPassword) {
        login = strLogin;
        password = strPassword;
        sessionId = "";
        sessionToken = "";
        registrationDTO = new RegistrationDTO(login, password);
    }

    @Given("A new User")
    public void aNewUser() {
        aUser("nouveau", "utilisateur");
    }

    @Given("A user already registered")
    public void aUserAlreadyRegistered() {
        aNewUser();
    }

    @Given("A user already registered but bad password")
    public void aUserAlreadyRegisteredButBadPassword() {
        aUser("nouveau", "erreur");
    }

    @Given("A user already login")
    public void aUserAlreadyLogin() {
        aUserAlreadyRegistered();
        tryLoginFirstTime();
        receivedSuccessLogin();
    }

    @Given("A user already get token")
    public void aUserAlreadyGetToken() {
        aUserAlreadyLogin();
        tryToGetToken();
        receivedSuccessTokenization();
    }

    @When("Try to login")
    public void tryLoginFirstTime() {
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
                .header("session", sessionId)
                .header("token", sessionToken)
                .when()
                .post("/logout");
    }

    @When("Try to authorize")
    public void tryToAuthorize() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("session", sessionId)
                .header("token", sessionToken)
                .when()
                .get("/authorize");
    }

    @When("Try to get token")
    public void tryToGetToken() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("session", sessionId)
                .when()
                .get("/token");
    }

    @Then("received error message unknown user")
    public void receivedErrorUnknownUser() {
        assertEquals(401, response.statusCode());
        assertEquals("{\"message\":\"Login failed\"}", response.getBody().asString());
    }

    @Then("received success register")
    public void receivedSuccessRegister() {
        assertEquals(201, response.statusCode());
        assertEquals("{\"message\":\"Registration succeed\"}", response.getBody().asString());
    }

    @Then("received success login")
    public void receivedSuccessLogin() {
        assertEquals(200, response.statusCode());
        assertEquals("{\"message\":\"Login succeed\"}", response.getBody().asString());
        sessionId = response.getHeader("session") == null ? null : response.getHeader("session");
    }

    @Then("received success tokenization")
    public void receivedSuccessTokenization() {
        assertEquals(200, response.statusCode());
        assertEquals("{\"message\":\"Tokenization succeed\"}", response.getBody().asString());
        sessionToken = response.getHeader("token") == null ? null : response.getHeader("token");
    }

    @Then("received failure tokenization")
    public void receivedFailureTokenization() {
        assertEquals(401, response.statusCode());
        assertEquals("{\"message\":\"Tokenization failed\"}", response.getBody().asString());
    }

    @Then("received failure register")
    public void receivedFailureRegister() {
        assertEquals(400, response.statusCode());
        assertEquals("{\"message\":\"Registration failed\"}", response.getBody().asString());
    }

    @Then("received success logout")
    public void receivedSuccessLogout() {
        assertEquals(200, response.statusCode());
        assertEquals("{\"message\":\"Logout succeed\"}", response.getBody().asString());
    }

    @Then("received success authorization")
    public void receivedSuccessAuthorization() {
        assertEquals(200, response.statusCode());
        assertEquals("{\"message\":\"Authorization succeed\"}", response.getBody().asString());
    }

    @Then("received failure authorization")
    public void receivedFailureAuthorization() {
        assertEquals(401, response.statusCode());
        assertEquals("{\"message\":\"Authorization failed\"}", response.getBody().asString());
    }
}
