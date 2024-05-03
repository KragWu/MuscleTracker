package fr.kragwu.muscletracker.userapi.services;

import fr.kragwu.muscletracker.userapi.adapters.SessionAdapter;
import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.repositories.SessionRepository;
import fr.kragwu.muscletracker.userapi.repositories.UserRepository;
import fr.kragwu.muscletracker.userapi.repositories.entities.Session;
import fr.kragwu.muscletracker.userapi.repositories.entities.User;
import fr.kragwu.muscletracker.userapi.security.Cipher;
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
        String id = UUID.randomUUID().toString();
        UserBO userBO = new UserBO();
        userBO.setId(id);
        userBO.setLogin("yrdy");
        userBO.setPassword("ozqqpef");
        userBO.setRegistrationDate(LocalDate.now());

        String userLogin = Cipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

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
    void registerUserKO() {
        // GIVEN
        String id = UUID.randomUUID().toString();
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

        String userLogin = Cipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

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

        User user = new User();
        user.setId("1");
        user.setLogin(userBO.getLogin());
        user.setPassword("password");
        user.setRegistrationDate(LocalDate.now());

        String userLogin = Cipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

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

        String userLogin = Cipher.decrypt(userBO.getLogin(), LocalDate.now().toString());

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
        SessionDTO sessionDTO = new SessionDTO();
        String id = UUID.randomUUID().toString();
        String idUser = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionDTO.setId(id);
        sessionDTO.setIdUser(idUser);
        sessionDTO.setToken(token);
        sessionDTO.setLoginDateTime(dateTime);
        sessionDTO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformDTOtoDAO(sessionDTO);

        when(sessionRepository.findByIdUser(sessionDTO.getIdUser())).thenReturn(Optional.of(session));

        // WHEN
        service.registerSession(sessionDTO);

        // THEN
        verify(sessionRepository).delete(any());
        verify(sessionRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(sessionDTO.getIdUser(), argumentCaptor.getValue().getIdUser());
        Assertions.assertEquals(sessionDTO.getId(), argumentCaptor.getValue().getId());
        Assertions.assertEquals(sessionDTO.getToken(), argumentCaptor.getValue().getToken());
        Assertions.assertEquals(sessionDTO.getLoginDateTime(), argumentCaptor.getValue().getLoginDateTime());
    }

    @Test
    void registerSessionCreated() {
        // GIVEN
        ArgumentCaptor<Session> argumentCaptor = ArgumentCaptor.forClass(Session.class);
        SessionDTO sessionDTO = new SessionDTO();
        String id = UUID.randomUUID().toString();
        String idUser = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionDTO.setId(id);
        sessionDTO.setIdUser(idUser);
        sessionDTO.setToken(token);
        sessionDTO.setLoginDateTime(dateTime);
        sessionDTO.setLogoutDateTime(null);

        when(sessionRepository.findByIdUser(sessionDTO.getIdUser())).thenReturn(Optional.empty());

        // WHEN
        service.registerSession(sessionDTO);

        // THEN
        verify(sessionRepository, times(0)).delete(any());
        verify(sessionRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(sessionDTO.getIdUser(), argumentCaptor.getValue().getIdUser());
        Assertions.assertEquals(sessionDTO.getId(), argumentCaptor.getValue().getId());
        Assertions.assertEquals(sessionDTO.getToken(), argumentCaptor.getValue().getToken());
        Assertions.assertEquals(sessionDTO.getLoginDateTime(), argumentCaptor.getValue().getLoginDateTime());
    }

    @Test
    void logoutSessionFound() {
        // GIVEN
        ArgumentCaptor<Session> argumentCaptor = ArgumentCaptor.forClass(Session.class);
        SessionDTO sessionDTO = new SessionDTO();
        String id = UUID.randomUUID().toString();
        String idUser = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionDTO.setId(id);
        sessionDTO.setIdUser(idUser);
        sessionDTO.setToken(token);
        sessionDTO.setLoginDateTime(dateTime);
        sessionDTO.setLogoutDateTime(dateTime);

        Session session = SessionAdapter.transformDTOtoDAO(sessionDTO);
        session.setLogoutDateTime(null);

        when(sessionRepository.findByIdUser(sessionDTO.getIdUser())).thenReturn(Optional.of(session));

        // WHEN
        service.logoutSession(sessionDTO);

        // THEN
        verify(sessionRepository).delete(any());
        verify(sessionRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(sessionDTO.getIdUser(), argumentCaptor.getValue().getIdUser());
        Assertions.assertEquals(sessionDTO.getId(), argumentCaptor.getValue().getId());
        Assertions.assertEquals(sessionDTO.getToken(), argumentCaptor.getValue().getToken());
        Assertions.assertEquals(sessionDTO.getLoginDateTime(), argumentCaptor.getValue().getLoginDateTime());
        Assertions.assertNotNull(argumentCaptor.getValue().getLogoutDateTime());
    }

    @Test
    void logoutSessionNotFound() {
        // GIVEN
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(null);
        sessionDTO.setIdUser(null);

        // WHEN
        service.logoutSession(sessionDTO);

        // THEN
        verify(sessionRepository, times(0)).delete(any());
        verify(sessionRepository, times(0)).save(any());
    }

    @Test
    void getSessionFoundByIdUser() {
        // GIVEN
        SessionDTO sessionDTO = new SessionDTO();
        String id = UUID.randomUUID().toString();
        String idUser = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionDTO.setId(id);
        sessionDTO.setIdUser(idUser);
        sessionDTO.setToken(token);
        sessionDTO.setLoginDateTime(dateTime);
        sessionDTO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformDTOtoDAO(sessionDTO);

        when(sessionRepository.findByIdUser(sessionDTO.getIdUser())).thenReturn(Optional.of(session));

        // WHEN
        Optional<SessionDTO> result = service.getSession(sessionDTO);

        // THEN
        verify(sessionRepository).findByIdUser(sessionDTO.getIdUser());
        verify(sessionRepository, times(0)).findById(any());
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(sessionDTO.getIdUser(), result.get().getIdUser());
        Assertions.assertEquals(sessionDTO.getToken(), result.get().getToken());
        Assertions.assertEquals(sessionDTO.getId(), result.get().getId());
        Assertions.assertEquals(sessionDTO.getLoginDateTime(), result.get().getLoginDateTime());
    }

    @Test
    void getSessionFoundById() {
        // GIVEN
        SessionDTO sessionDTO = new SessionDTO();
        String id = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionDTO.setId(id);
        sessionDTO.setIdUser(null);
        sessionDTO.setToken(token);
        sessionDTO.setLoginDateTime(dateTime);
        sessionDTO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformDTOtoDAO(sessionDTO);

        when(sessionRepository.findById(sessionDTO.getId())).thenReturn(Optional.of(session));

        // WHEN
        Optional<SessionDTO> result = service.getSession(sessionDTO);

        // THEN
        verify(sessionRepository).findById(sessionDTO.getId());
        verify(sessionRepository, times(0)).findByIdUser(any());
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(sessionDTO.getIdUser(), result.get().getIdUser());
        Assertions.assertEquals(sessionDTO.getToken(), result.get().getToken());
        Assertions.assertEquals(sessionDTO.getId(), result.get().getId());
        Assertions.assertEquals(sessionDTO.getLoginDateTime(), result.get().getLoginDateTime());
    }

    @Test
    void getSessionNotFound() {
        // GIVEN
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(null);
        sessionDTO.setIdUser(null);

        // WHEN
        Optional<SessionDTO> result = service.getSession(sessionDTO);

        // THEN
        verifyNoInteractions(sessionRepository);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getSessionNotFoundBecauseNull() {
        // GIVEN

        // WHEN
        Optional<SessionDTO> result = service.getSession(null);

        // THEN
        verifyNoInteractions(sessionRepository);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void isAuthorize() {
        // GIVEN
        SessionDTO sessionDTO = new SessionDTO();
        String id = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionDTO.setId(id);
        sessionDTO.setIdUser(null);
        sessionDTO.setToken(token);
        sessionDTO.setLoginDateTime(dateTime);
        sessionDTO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformDTOtoDAO(sessionDTO);

        when(sessionRepository.findById(sessionDTO.getId())).thenReturn(Optional.of(session));
        // WHEN
        Optional<SessionDTO> result = service.authorize(sessionDTO);
        // THEN
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(sessionDTO.getIdUser(), result.get().getIdUser());
        Assertions.assertEquals(sessionDTO.getToken(), result.get().getToken());
        Assertions.assertEquals(sessionDTO.getId(), result.get().getId());
        Assertions.assertEquals(sessionDTO.getLoginDateTime(), result.get().getLoginDateTime());
    }

    @Test
    void notAuthorize() {
        // GIVEN
        SessionDTO sessionDTO = new SessionDTO();
        String id = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        sessionDTO.setId(id);
        sessionDTO.setIdUser(null);
        sessionDTO.setToken(token);
        sessionDTO.setLoginDateTime(dateTime);
        sessionDTO.setLogoutDateTime(null);

        Session session = SessionAdapter.transformDTOtoDAO(sessionDTO);
        session.setLoginDateTime(session.getLoginDateTime().minusHours(3));

        when(sessionRepository.findById(sessionDTO.getId())).thenReturn(Optional.of(session));

        // WHEN
        Optional<SessionDTO> result = service.authorize(sessionDTO);
        // THEN
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void notAuthorizeBecauseSessionIdNull() {
        // GIVEN
        SessionDTO sessionDTO = new SessionDTO();
        // WHEN
        Optional<SessionDTO> result = service.authorize(sessionDTO);
        // THEN
        Assertions.assertTrue(result.isEmpty());
        verifyNoInteractions(sessionRepository);
    }
}
