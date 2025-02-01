package fr.kragwu.muscletracker.userapi.controllers;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.http.MediaType;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.controllers.dto.StatusDTO;
import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import fr.kragwu.muscletracker.userapi.services.UserService;

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
        assertEquals(responseBody, "API User started");
    }

    
    @Test
    void login_succeed() {
        String login = "user";
        String password = "m0t2p45Se";
        UserBO userBO = new UserBO("1", login, password, LocalDate.now());
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setIdUser(userBO.getId());
        String body = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", login, password);
        
        Mockito.when(service.getUser(any())).thenReturn(Optional.of(userBO));
        Mockito.doNothing().when(service).registerSession(sessionDTO);
        
        SessionDTO responseBody = client.post()
        .uri("/login")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body))
        .exchange()
        .expectStatus().isOk()
        .expectBody(SessionDTO.class).returnResult().getResponseBody();
        
        assertNotNull(responseBody);
        assertEquals(responseBody.getIdUser(), sessionDTO.getIdUser());
        verify(service).getUser(any());
        verify(service).registerSession(any());
    }

    @Test
    void login_failed_user_not_found() {
        String login = "user";
        String password = "m0t2p45Se";
        String body = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", login, password);
        
        Mockito.when(service.getUser(any())).thenReturn(Optional.empty());

        SessionDTO responseBody = client.post()
            .uri("/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(body))
            .exchange()
            .expectStatus().isUnauthorized()
            .expectBody(SessionDTO.class).returnResult().getResponseBody();

        assertNull(responseBody);
        verify(service).getUser(any());
        verify(service, times(0)).registerSession(any());
    }

    @Test
    void login_failed_bad_message() {
        String login = "user";
        String password = "m0t2p45Se";
        String body = String.format("{ \"lo\": \"%s\", \"password\": \"%s\" }", login, password);
        
        SessionDTO responseBody = client.post()
            .uri("/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(body))
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(SessionDTO.class).returnResult().getResponseBody();

        assertNull(responseBody);
        verify(service, times(0)).getUser(any());
        verify(service, times(0)).registerSession(any());
    }
    

    @Test
    void register_succeed() {
        String login = "user";
        String password = "m0t2p45Se";
        UserBO userBO = new UserBO("1", login, password, LocalDate.now());
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setIdUser(userBO.getId());
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
        assertEquals("OK", responseBody.getMessage());
        verify(service).registerUser(any());
    }

    @Test
    void register_failed() {
        String login = "user";
        String password = "m0t2p45Se";
        UserBO userBO = new UserBO("1", login, password, LocalDate.now());
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setIdUser(userBO.getId());
        String body = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", login, password);
        
        Mockito.when(service.registerUser(any())).thenReturn(false);
        
        String responseBody = client.post()
        .uri("/register")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body))
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(String.class).returnResult().getResponseBody();
        
        assertNull(responseBody);
        verify(service).registerUser(any());
    }

    @Test
    void register_with_bad_format() {
        String login = "user";
        String password = "m0t2p45Se";
        String body = String.format("{ \"lo\": \"%s\", \"password\": \"%s\" }", login, password);
        
        SessionDTO responseBody = client.post()
            .uri("/register")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(body))
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(SessionDTO.class).returnResult().getResponseBody();

        assertNull(responseBody);
        verify(service, times(0)).registerUser(any());
    }

    @Test
    void logout() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();
        String body = String.format("{ \"id\": \"%s\", \"token\": \"%s\" }", idSession, tokenSession);
        
        Mockito.doNothing().when(service).logoutSession(any());
        
        StatusDTO responseBody = client.post()
        .uri("/logout")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body))
        .exchange()
        .expectStatus().isOk()
        .expectBody(StatusDTO.class).returnResult().getResponseBody();
        
        assertNotNull(responseBody);
        assertEquals("OK", responseBody.getMessage());
        verify(service).logoutSession(any());
    }

    @Test
    void logout_bad_format() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();
        String body = String.format("{ \"id\": \"%s\", \"tok\": \"%s\" }", idSession, tokenSession);
                
        String responseBody = client.post()
        .uri("/logout")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body))
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(String.class).returnResult().getResponseBody();
        
        assertNull(responseBody);
        verify(service, times(0)).logoutSession(any());
    }

    @Test
    void authorize_succeed() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(idSession);
        sessionDTO.setToken(tokenSession);
        String body = String.format("{ \"id\": \"%s\", \"token\": \"%s\" }", idSession, tokenSession);
        
        Mockito.when(service.authorize(any())).thenReturn(Optional.of(sessionDTO));
        
        StatusDTO responseBody = client.post()
        .uri("/authorize")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body))
        .exchange()
        .expectStatus().isOk()
        .expectBody(StatusDTO.class).returnResult().getResponseBody();
        
        assertNotNull(responseBody);
        assertEquals("OK", responseBody.getMessage());
        verify(service).authorize(any());
    }

    @Test
    void not_authorize() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();
        String body = String.format("{ \"id\": \"%s\", \"token\": \"%s\" }", idSession, tokenSession);
        
        Mockito.when(service.authorize(any())).thenReturn(Optional.empty());
        
        String responseBody = client.post()
        .uri("/authorize")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body))
        .exchange()
        .expectStatus().isUnauthorized()
        .expectBody(String.class).returnResult().getResponseBody();
        
        assertNull(responseBody);
        verify(service).authorize(any());
    }

    @Test
    void authorize_bad_format() {
        String idSession = UUID.randomUUID().toString();
        String tokenSession = UUID.randomUUID().toString();
        String body = String.format("{ \"id\": \"%s\", \"ton\": \"%s\" }", idSession, tokenSession);
                
        String responseBody = client.post()
        .uri("/authorize")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body))
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(String.class).returnResult().getResponseBody();
        
        assertNull(responseBody);
        verify(service, times(0)).authorize(any());
    }
}
