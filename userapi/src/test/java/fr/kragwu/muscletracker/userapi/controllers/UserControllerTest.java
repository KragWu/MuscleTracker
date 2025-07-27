package fr.kragwu.muscletracker.userapi.controllers;

import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.controllers.dto.StatusDTO;
import fr.kragwu.muscletracker.userapi.security.MyCipher;
import fr.kragwu.muscletracker.userapi.services.UserService;
import fr.kragwu.muscletracker.userapi.services.bo.SessionBO;
import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserControllerTest {

    private UserService service;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        service = mock(UserService.class);
        UserController controller = new UserController(service);
        client = WebTestClient.bindToController(controller).configureClient().responseTimeout(Duration.ofMinutes(1)).build();
    }

    @Test
    void hello() {
        String responseBody = client.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).returnResult().getResponseBody();
        assertEquals("API User started", responseBody);
    }


    @Test
    void login_succeed() {
        String login = "user";
        String password = "m0t2p45Se";
        UUID id = UUID.randomUUID();
        UserBO userBO = new UserBO(id, login, password, LocalDate.now());
        SessionBO sessionBO = new SessionBO();
        sessionBO.setIdUser(userBO.getId());
        String body = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", login, password);

        Mockito.when(service.getUser(any())).thenReturn(Optional.of(userBO));
        Mockito.doNothing().when(service).registerSession(sessionBO);

        StatusDTO responseBody = client.post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isOk()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        verify(service).getUser(any());
        verify(service).registerSession(any());
    }

    @Test
    void login_failed_user_not_found() {
        String login = "user";
        String password = "m0t2p45Se";
        String body = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", login, password);

        Mockito.when(service.getUser(any())).thenReturn(Optional.empty());

        StatusDTO responseBody = client.post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        verify(service).getUser(any());
        verify(service, times(0)).registerSession(any());
    }

    @Test
    void login_failed_bad_message() {
        String login = "user";
        String password = "m0t2p45Se";
        String body = String.format("{ \"lo\": \"%s\", \"password\": \"%s\" }", login, password);

        StatusDTO responseBody = client.post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNull(responseBody);
        verify(service, times(0)).getUser(any());
        verify(service, times(0)).registerSession(any());
    }


    @Test
    void register_succeed() {
        String login = "user";
        String password = "m0t2p45Se";
        String body = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", login, password);

        Mockito.when(service.registerUser(any())).thenReturn(true);

        StatusDTO responseBody = client.post()
                .uri("/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        assertEquals("Registration succeed", responseBody.getMessage());
        verify(service).registerUser(any());
    }

    @Test
    void register_failed() {
        String login = "user";
        String password = "m0t2p45Se";
        String body = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", login, password);

        Mockito.when(service.registerUser(any())).thenReturn(false);

        StatusDTO responseBody = client.post()
                .uri("/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        assertEquals("Registration failed", responseBody.getMessage());
        verify(service).registerUser(any());
    }

    @Test
    void register_with_bad_format() {
        String login = "user";
        String password = "m0t2p45Se";
        String body = String.format("{ \"lo\": \"%s\", \"password\": \"%s\" }", login, password);

        StatusDTO responseBody = client.post()
                .uri("/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNull(responseBody);
        verify(service, times(0)).registerUser(any());
    }

    @Test
    void logout() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();

        Mockito.doNothing().when(service).logoutSession(any());

        StatusDTO responseBody = client.post()
                .uri("/logout")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("session", idSession)
                .header("token", tokenSession)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        assertEquals("Logout succeed", responseBody.getMessage());
        verify(service).logoutSession(any());
    }

    @Test
    void logout_bad_format() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();
        String body = String.format("{ \"id\": \"%s\", \"tok\": \"%s\" }", idSession, tokenSession);

        StatusDTO responseBody = client.post()
                .uri("/logout")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNull(responseBody);
        verify(service, times(0)).logoutSession(any());
    }

    @Test
    void authorize_succeed() {
        UUID idSession = UUID.randomUUID();
        String idSessionSecret = idSession.toString();
        String tokenSession = UUID.randomUUID().toString();
        SessionBO sessionBO = new SessionBO();
        sessionBO.setId(idSession);
        sessionBO.setToken(tokenSession);

        Mockito.when(service.authorize(any())).thenReturn(Optional.of(sessionBO));

        StatusDTO responseBody = client.post()
                .uri("/authorize")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("session", idSessionSecret)
                .header("token", tokenSession)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        assertEquals("Authorization succeed", responseBody.getMessage());
        verify(service).authorize(any());
    }

    @Test
    void tokenization_succeed() {
        UUID idSession = UUID.randomUUID();
        String idSessionSecret = idSession.toString();
        String tokenSession = UUID.randomUUID().toString();
        SessionBO sessionBO = new SessionBO();
        sessionBO.setId(idSession);
        sessionBO.setToken(tokenSession);

        Mockito.when(service.getSession(any())).thenReturn(Optional.of(sessionBO));

        StatusDTO responseBody = client.get()
                .uri("/token")
                .accept(MediaType.APPLICATION_JSON)
                .header("session", idSessionSecret)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        assertEquals("Tokenization succeed", responseBody.getMessage());
        verify(service).getSession(any());
    }

    @Test
    void tokenization_failure() {
        UUID idSession = UUID.randomUUID();
        String idSessionSecret = idSession.toString();

        Mockito.when(service.getSession(any())).thenReturn(Optional.empty());

        StatusDTO responseBody = client.get()
                .uri("/token")
                .accept(MediaType.APPLICATION_JSON)
                .header("session", idSessionSecret)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        assertEquals("Tokenization failed", responseBody.getMessage());
        verify(service).getSession(any());
    }

    @Test
    void not_authorize() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();

        Mockito.when(service.authorize(any())).thenReturn(Optional.empty());

        StatusDTO responseBody = client.post()
                .uri("/authorize")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("session", idSession)
                .header("token", tokenSession)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNotNull(responseBody);
        assertEquals("Authorization failed", responseBody.getMessage());
        verify(service).authorize(any());
    }

    @Test
    void authorize_bad_format() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();
        String body = String.format("{ \"id\": \"%s\", \"ton\": \"%s\" }", idSession, tokenSession);

        StatusDTO responseBody = client.post()
                .uri("/authorize")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(StatusDTO.class).returnResult().getResponseBody();

        assertNull(responseBody);
        verify(service, times(0)).authorize(any());
    }
}
