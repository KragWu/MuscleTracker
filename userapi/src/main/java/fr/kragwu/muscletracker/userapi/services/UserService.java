package fr.kragwu.muscletracker.userapi.services;

import fr.kragwu.muscletracker.userapi.adapters.SessionAdapter;
import fr.kragwu.muscletracker.userapi.adapters.UserAdapter;
import fr.kragwu.muscletracker.userapi.repositories.SessionRepository;
import fr.kragwu.muscletracker.userapi.repositories.UserRepository;
import fr.kragwu.muscletracker.userapi.repositories.entities.Session;
import fr.kragwu.muscletracker.userapi.repositories.entities.User;
import fr.kragwu.muscletracker.userapi.security.MyCipher;
import fr.kragwu.muscletracker.userapi.services.bo.SessionBO;
import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import fr.kragwu.muscletracker.userapi.services.exception.BadCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final SessionRepository sessionRepository;

    public UserService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public boolean registerUser(UserBO userBO) {
        Optional<UserBO> optUser;
        try {
            optUser = getUser(userBO);
            if (optUser.isEmpty()) {
                String currentDate = LocalDate.now().toString();
                String userLogin = MyCipher.decrypt(userBO.getLogin(), currentDate);
                userBO.setLogin(userLogin);
                userRepository.save(UserAdapter.transformBOtoDAO(userBO));
            } else {
                return false;
            }
        } catch (BadCredentialsException bce){
            log.error(bce.getMessage());
            return false;
        }
        return true;
    }

    public Optional<UserBO> getUser(UserBO userBO) throws BadCredentialsException {
        AtomicReference<Optional<UserBO>> result = new AtomicReference<>(Optional.empty());
        if (userBO != null && userBO.getLogin() != null) {
            String currentDate = LocalDate.now().toString();
            String userBOLogin = MyCipher.decrypt(userBO.getLogin(), currentDate);
            Optional<User> optUser = userRepository.findByLogin(userBOLogin);
            optUser.ifPresentOrElse(user -> {
                        String userBOPassword = MyCipher.decrypt(userBO.getPassword(), currentDate);
                        String userDAOPassword = MyCipher.decrypt(user.getPassword(), user.getRegistrationDate().toString());
                        if (userBOPassword.equals(userDAOPassword)) {
                            result.set(Optional.of(UserAdapter.transformDAOtoBO(user)));
                        } else {
                            throw new BadCredentialsException("Bad credentials given");
                        }
                    },
                    () ->
                        log.info("User not found")
                    );
        }
        return result.get();
    }

    public void registerSession(SessionBO sessionBO) {
        Optional<SessionBO> optSessionBO = getSession(sessionBO);
        optSessionBO.ifPresentOrElse(sessionDTOFind -> {
            log.info("Session found");
            sessionRepository.delete(SessionAdapter.transformBOtoDAO(sessionDTOFind));
            sessionDTOFind = sessionDTOFind.update(sessionBO);
            sessionRepository.save(SessionAdapter.transformBOtoDAO(sessionDTOFind));
        }, () -> {
            log.info("Session not found");
            sessionRepository.save(SessionAdapter.transformBOtoDAO(sessionBO));
        });
    }

    public void logoutSession(SessionBO sessionBO) {
        Optional<SessionBO> optSessionBO = getSession(sessionBO);
        optSessionBO.ifPresent(sessionBOFind -> {
            sessionRepository.delete(SessionAdapter.transformBOtoDAO(sessionBOFind));
            sessionBOFind = sessionBO.update(sessionBOFind);
            sessionRepository.save(SessionAdapter.transformBOtoDAO(sessionBOFind));
        });
    }

    public Optional<SessionBO> getSession(SessionBO sessionBO) {
        AtomicReference<Optional<SessionBO>> result = new AtomicReference<>(Optional.empty());
        if (sessionBO != null) {
            if(sessionBO.getIdUser() != null) {
                Optional<Session> optSession = sessionRepository.findByIdUser(sessionBO.getIdUser());
                optSession.ifPresentOrElse(session -> result.set(Optional.of(SessionAdapter.transformDAOtoBO(session))),
                        () -> log.info("Pas de session par IdUser"));
            }
            if(result.get().isEmpty() && sessionBO.getId() != null) {
                Optional<Session> optSession = sessionRepository.findById(sessionBO.getId());
                optSession.ifPresentOrElse(session -> result.set(Optional.of(SessionAdapter.transformDAOtoBO(session))),
                        () -> log.info("Pas de session par Id"));
            }
        }
        return result.get();
    }

    public Optional<SessionBO> authorize(SessionBO sessionBO) {
        AtomicReference<Optional<SessionBO>> result = new AtomicReference<>(Optional.empty());
        if (sessionBO != null && sessionBO.getId() != null && sessionBO.getToken() != null) {
            Optional<Session> optSession = sessionRepository.findById(sessionBO.getId());
            optSession.ifPresent(session -> {
                if (sessionBO.getToken().equals(session.getToken())) {
                    if (sessionBO.getLoginDateTime().isBefore(
                            session.getLoginDateTime().plusHours(2))) {
                        result.set(Optional.of(SessionAdapter.transformDAOtoBO(session)));
                    }
                }
            });
        }
        return result.get();
    }
}
