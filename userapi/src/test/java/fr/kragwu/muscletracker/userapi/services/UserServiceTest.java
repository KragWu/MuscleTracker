package fr.kragwu.muscletracker.userapi.services;

import fr.kragwu.muscletracker.userapi.adapters.SessionAdapter;
import fr.kragwu.muscletracker.userapi.repositories.SessionRepository;
import fr.kragwu.muscletracker.userapi.repositories.UserRepository;
import fr.kragwu.muscletracker.userapi.repositories.entities.Session;
import fr.kragwu.muscletracker.userapi.repositories.entities.User;
import fr.kragwu.muscletracker.userapi.security.MyCipher;
import fr.kragwu.muscletracker.userapi.services.bo.SessionBO;
import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService service;

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        sessionRepository = mock(SessionRepository.class);
        service = new UserService(userRepository, sessionRepository);
    }

    @Test
    void registerUserOK() {
        // GIVEN
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        UUID id = UUID.randomUUID();
        UserBO userBO = new UserBO();
        userBO.setId(id);
        userBO.setLogin("yrdy");
        userBO.setPassword("ozqqpef");
        userBO.setRegistrationDate(LocalDate.now());

        String userLogin = MyCipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.empty());

        // WHEN
        boolean result = service.registerUser(userBO);

        // THEN
        verify(userRepository).save(argumentCaptor.capture());
        Assertions.assertTrue(result);
        Assertions.assertEquals(id, argumentCaptor.getValue().getId());
        Assertions.assertEquals(userLogin, argumentCaptor.getValue().getLogin());
        Assertions.assertEquals(userBO.getPassword(), argumentCaptor.getValue().getPassword());
        Assertions.assertEquals(LocalDate.now(), argumentCaptor.getValue().getRegistrationDate());
    }

    @Test
    void registerUser_BadCredentials() {
        // GIVEN
        UUID id = UUID.randomUUID();
        UserBO userBO = new UserBO();
        userBO.setId(id);
        userBO.setLogin("yrdy");
        userBO.setPassword("ozqqpef");
        userBO.setRegistrationDate(LocalDate.now());

        User user = new User();
        user.setId(id);
        user.setLogin(userBO.getLogin());
        user.setPassword("other");
        user.setRegistrationDate(userBO.getRegistrationDate());

        String userLogin = MyCipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(user));

        // WHEN
        boolean result = service.registerUser(userBO);

        // THEN
        verify(userRepository, times(0)).save(any());
        Assertions.assertFalse(result);
    }

    @Test
    void registerUser_AlreadyExist() {
        // GIVEN
        UUID id = UUID.randomUUID();
        UserBO userBO = new UserBO();
        userBO.setId(id);
        userBO.setLogin("yrdy");
        userBO.setPassword("ozqqpef");
        userBO.setRegistrationDate(LocalDate.now());

        User user = new User();
        user.setId(id);
        user.setLogin(userBO.getLogin());
        user.setPassword(userBO.getPassword());
        user.setRegistrationDate(userBO.getRegistrationDate());

        String userLogin = MyCipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(user));

        // WHEN
        boolean result = service.registerUser(userBO);

        // THEN
        verify(userRepository, times(0)).save(any());
        Assertions.assertFalse(result);
    }

    @Test
    void getUserFound() {
        // GIVEN
        UserBO userBO = new UserBO();
        userBO.setLogin("test");
        userBO.setPassword("password");
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setId(id);
        user.setLogin(userBO.getLogin());
        user.setPassword("password");
        user.setRegistrationDate(LocalDate.now());

        String userLogin = MyCipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(user));

        // WHEN
        Optional<UserBO> result = service.getUser(userBO);

        // THEN
        verify(userRepository).findByLogin(userLogin);
        verifyNoMoreInteractions(userRepository);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(userBO.getLogin(), result.get().getLogin());
        Assertions.assertEquals(userBO.getPassword(), result.get().getPassword());
        Assertions.assertEquals(user.getId(), result.get().getId());
        Assertions.assertEquals(user.getRegistrationDate(), result.get().getRegistrationDate());
    }

    @Test
    void getUserNotFound() {
        // GIVEN
        UserBO userBO = new UserBO();
        userBO.setLogin("test");
        userBO.setPassword("password");

        String userLogin = MyCipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.empty());

        // WHEN
        Optional<UserBO> result = service.getUser(userBO);

        // THEN
        verify(userRepository).findByLogin(userLogin);
        verifyNoMoreInteractions(userRepository);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void registerSessionUpdated() {
        // GIVEN
        ArgumentCaptor<Session> argumentCaptor = ArgumentCaptor.forClass(Session.class);
        SessionBO sessionBO = new SessionBO();
        UUID id = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionBO.setId(id);
        sessionBO.setIdUser(idUser);
        sessionBO.setToken(token);
        sessionBO.setLoginDateTime(dateTime);
        sessionBO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformBOtoDAO(sessionBO);

        when(sessionRepository.findByIdUser(sessionBO.getIdUser())).thenReturn(Optional.of(session));

        // WHEN
        service.registerSession(sessionBO);

        // THEN
        verify(sessionRepository).delete(any());
        verify(sessionRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(sessionBO.getIdUser(), argumentCaptor.getValue().getIdUser());
        Assertions.assertEquals(sessionBO.getId(), argumentCaptor.getValue().getId());
        Assertions.assertEquals(sessionBO.getToken(), argumentCaptor.getValue().getToken());
        Assertions.assertEquals(sessionBO.getLoginDateTime(), argumentCaptor.getValue().getLoginDateTime());
    }

    @Test
    void registerSessionCreated() {
        // GIVEN
        ArgumentCaptor<Session> argumentCaptor = ArgumentCaptor.forClass(Session.class);
        SessionBO sessionBO = new SessionBO();
        UUID id = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionBO.setId(id);
        sessionBO.setIdUser(idUser);
        sessionBO.setToken(token);
        sessionBO.setLoginDateTime(dateTime);
        sessionBO.setLogoutDateTime(null);

        when(sessionRepository.findByIdUser(sessionBO.getIdUser())).thenReturn(Optional.empty());

        // WHEN
        service.registerSession(sessionBO);

        // THEN
        verify(sessionRepository, times(0)).delete(any());
        verify(sessionRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(sessionBO.getIdUser(), argumentCaptor.getValue().getIdUser());
        Assertions.assertEquals(sessionBO.getId(), argumentCaptor.getValue().getId());
        Assertions.assertEquals(sessionBO.getToken(), argumentCaptor.getValue().getToken());
        Assertions.assertEquals(sessionBO.getLoginDateTime(), argumentCaptor.getValue().getLoginDateTime());
    }

    @Test
    void logoutSessionFound() {
        // GIVEN
        ArgumentCaptor<Session> argumentCaptor = ArgumentCaptor.forClass(Session.class);
        SessionBO sessionBO = new SessionBO();
        UUID id = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionBO.setId(id);
        sessionBO.setIdUser(idUser);
        sessionBO.setToken(token);
        sessionBO.setLoginDateTime(dateTime);
        sessionBO.setLogoutDateTime(dateTime);

        Session session = SessionAdapter.transformBOtoDAO(sessionBO);
        session.setLogoutDateTime(null);

        when(sessionRepository.findByIdUser(sessionBO.getIdUser())).thenReturn(Optional.of(session));

        // WHEN
        service.logoutSession(sessionBO);

        // THEN
        verify(sessionRepository).delete(any());
        verify(sessionRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(sessionBO.getIdUser(), argumentCaptor.getValue().getIdUser());
        Assertions.assertEquals(sessionBO.getId(), argumentCaptor.getValue().getId());
        Assertions.assertEquals(sessionBO.getToken(), argumentCaptor.getValue().getToken());
        Assertions.assertEquals(sessionBO.getLoginDateTime(), argumentCaptor.getValue().getLoginDateTime());
        Assertions.assertNotNull(argumentCaptor.getValue().getLogoutDateTime());
    }

    @Test
    void logoutSessionNotFound() {
        // GIVEN
        SessionBO sessionBO = new SessionBO();
        sessionBO.setId(null);
        sessionBO.setIdUser(null);

        // WHEN
        service.logoutSession(sessionBO);

        // THEN
        verify(sessionRepository, times(0)).delete(any());
        verify(sessionRepository, times(0)).save(any());
    }

    @Test
    void getSessionFoundByIdUser() {
        // GIVEN
        SessionBO sessionBO = new SessionBO();
        UUID id = UUID.randomUUID();
        UUID idUser = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionBO.setId(id);
        sessionBO.setIdUser(idUser);
        sessionBO.setToken(token);
        sessionBO.setLoginDateTime(dateTime);
        sessionBO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformBOtoDAO(sessionBO);

        when(sessionRepository.findByIdUser(sessionBO.getIdUser())).thenReturn(Optional.of(session));

        // WHEN
        Optional<SessionBO> result = service.getSession(sessionBO);

        // THEN
        verify(sessionRepository).findByIdUser(sessionBO.getIdUser());
        verify(sessionRepository, times(0)).findById(any());
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(sessionBO.getIdUser(), result.get().getIdUser());
        Assertions.assertEquals(sessionBO.getToken(), result.get().getToken());
        Assertions.assertEquals(sessionBO.getId(), result.get().getId());
        Assertions.assertEquals(sessionBO.getLoginDateTime(), result.get().getLoginDateTime());
    }

    @Test
    void getSessionFoundById() {
        // GIVEN
        SessionBO sessionBO = new SessionBO();
        UUID id = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionBO.setId(id);
        sessionBO.setIdUser(null);
        sessionBO.setToken(token);
        sessionBO.setLoginDateTime(dateTime);
        sessionBO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformBOtoDAO(sessionBO);

        when(sessionRepository.findById(sessionBO.getId())).thenReturn(Optional.of(session));

        // WHEN
        Optional<SessionBO> result = service.getSession(sessionBO);

        // THEN
        verify(sessionRepository).findById(sessionBO.getId());
        verify(sessionRepository, times(0)).findByIdUser(any());
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(sessionBO.getIdUser(), result.get().getIdUser());
        Assertions.assertEquals(sessionBO.getToken(), result.get().getToken());
        Assertions.assertEquals(sessionBO.getId(), result.get().getId());
        Assertions.assertEquals(sessionBO.getLoginDateTime(), result.get().getLoginDateTime());
    }

    @Test
    void getSessionNotFound() {
        // GIVEN
        SessionBO sessionBO = new SessionBO();
        sessionBO.setId(null);
        sessionBO.setIdUser(null);

        // WHEN
        Optional<SessionBO> result = service.getSession(sessionBO);

        // THEN
        verifyNoInteractions(sessionRepository);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getSessionNotFoundBecauseNull() {
        // GIVEN

        // WHEN
        Optional<SessionBO> result = service.getSession(null);

        // THEN
        verifyNoInteractions(sessionRepository);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void isAuthorize() {
        // GIVEN
        SessionBO sessionBO = new SessionBO();
        UUID id = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionBO.setId(id);
        sessionBO.setIdUser(null);
        sessionBO.setToken(token);
        sessionBO.setLoginDateTime(dateTime);
        sessionBO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformBOtoDAO(sessionBO);

        when(sessionRepository.findById(sessionBO.getId())).thenReturn(Optional.of(session));
        // WHEN
        Optional<SessionBO> result = service.authorize(sessionBO);
        // THEN
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(sessionBO.getIdUser(), result.get().getIdUser());
        Assertions.assertEquals(sessionBO.getToken(), result.get().getToken());
        Assertions.assertEquals(sessionBO.getId(), result.get().getId());
        Assertions.assertEquals(sessionBO.getLoginDateTime(), result.get().getLoginDateTime());
    }

    @Test
    void notAuthorize() {
        // GIVEN
        SessionBO sessionBO = new SessionBO();
        UUID id = UUID.randomUUID();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionBO.setId(id);
        sessionBO.setIdUser(null);
        sessionBO.setToken(token);
        sessionBO.setLoginDateTime(dateTime);
        sessionBO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformBOtoDAO(sessionBO);
        session.setLoginDateTime(session.getLoginDateTime().minusHours(3));

        when(sessionRepository.findById(sessionBO.getId())).thenReturn(Optional.of(session));

        // WHEN
        Optional<SessionBO> result = service.authorize(sessionBO);
        // THEN
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void notAuthorizeBecauseSessionIdNull() {
        // GIVEN
        SessionBO sessionBO = new SessionBO();
        // WHEN
        Optional<SessionBO> result = service.authorize(sessionBO);
        // THEN
        Assertions.assertTrue(result.isEmpty());
        verifyNoInteractions(sessionRepository);
    }
}
