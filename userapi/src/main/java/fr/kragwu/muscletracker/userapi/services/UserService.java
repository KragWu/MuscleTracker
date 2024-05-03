package fr.kragwu.muscletracker.userapi.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import fr.kragwu.muscletracker.userapi.adapters.SessionAdapter;
import fr.kragwu.muscletracker.userapi.adapters.UserAdapter;
import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import fr.kragwu.muscletracker.userapi.repositories.SessionRepository;
import fr.kragwu.muscletracker.userapi.repositories.UserRepository;
import fr.kragwu.muscletracker.userapi.repositories.entities.Session;
import fr.kragwu.muscletracker.userapi.repositories.entities.User;

import org.springframework.stereotype.Service;

import fr.kragwu.muscletracker.userapi.security.Cipher;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final SessionRepository sessionRepository;

    public UserService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public boolean registerUser(UserBO userBO) {
        Optional<UserBO> optUser = getUser(userBO);
        if (optUser.isEmpty()) {
            String currentDate = LocalDate.now().toString();
            String userLogin = Cipher.decrypt(userBO.getLogin(), currentDate);
            userBO.setLogin(userLogin);
            userRepository.save(UserAdapter.transformDTOtoDAO(userBO));
        }
        return optUser.isEmpty();
    }

    public Optional<UserBO> getUser(UserBO userBO) {
        AtomicReference<Optional<UserBO>> result = new AtomicReference<>(Optional.empty());
        if (userBO != null && userBO.getLogin() != null) {
            String currentDate = LocalDate.now().toString();
            String userLogin = Cipher.decrypt(userBO.getLogin(), currentDate);
            Optional<User> optUser = userRepository.findByLogin(userLogin);
            optUser.ifPresentOrElse(user -> {
                        String userPassword = Cipher.decrypt(userBO.getPassword(), currentDate);
                        String userListPassword = Cipher.decrypt(user.getPassword(), user.getRegistrationDate().toString());
                        if (userPassword.equals(userListPassword)) {
                            result.set(Optional.of(UserAdapter.transformDAOtoDTO(user)));
                        } else {
                            System.out.println("Bad password given");
                        }
                    },
                    () ->
                        System.out.println("User not found")
                    );
        }
        return result.get();
    }

    public void registerSession(SessionDTO sessionDTO) {
        Optional<SessionDTO> optSessionDTO = getSession(sessionDTO);
        optSessionDTO.ifPresentOrElse(sessionDTOFind -> {
            System.out.println("Session found");
            sessionRepository.delete(SessionAdapter.transformDTOtoDAO(sessionDTOFind));
            sessionDTOFind = sessionDTOFind.update(sessionDTO);
            sessionRepository.save(SessionAdapter.transformDTOtoDAO(sessionDTOFind));
        }, () -> {
            System.out.println("Session not found");
            sessionRepository.save(SessionAdapter.transformDTOtoDAO(sessionDTO));
        });
    }

    public void logoutSession(SessionDTO sessionDTO) {
        Optional<SessionDTO> optSessionDTO = getSession(sessionDTO);
        optSessionDTO.ifPresent(sessionDTOFind -> {
            sessionRepository.delete(SessionAdapter.transformDTOtoDAO(sessionDTOFind));
            sessionDTOFind = sessionDTO.update(sessionDTOFind);
            sessionRepository.save(SessionAdapter.transformDTOtoDAO(sessionDTOFind));
        });
    }

    public Optional<SessionDTO> getSession(SessionDTO sessionDTO) {
        AtomicReference<Optional<SessionDTO>> result = new AtomicReference<>(Optional.empty());
        if (sessionDTO != null) {
            System.out.println("SessionDTO : " + sessionDTO);
            if(sessionDTO.getIdUser() != null) {
                Optional<Session> optSession = sessionRepository.findByIdUser(sessionDTO.getIdUser());
                optSession.ifPresentOrElse(session -> result.set(Optional.of(SessionAdapter.transformDAOtoDTO(session))),
                        () -> System.out.println("Pas de session par IdUser"));
            }
            if(result.get().isEmpty() && sessionDTO.getId() != null) {
                Optional<Session> optSession = sessionRepository.findById(sessionDTO.getId());
                optSession.ifPresentOrElse(session -> result.set(Optional.of(SessionAdapter.transformDAOtoDTO(session))),
                        () -> System.out.println("Pas de session par Id"));
            }
        }
        return result.get();
    }

    public Optional<SessionDTO> authorize(SessionDTO sessionDTO) {
        AtomicReference<Optional<SessionDTO>> result = new AtomicReference<>(Optional.empty());
        if (sessionDTO != null && sessionDTO.getId() != null && sessionDTO.getToken() != null) {
            Optional<Session> optSession = sessionRepository.findById(sessionDTO.getId());
            optSession.ifPresent(session -> {
                if (sessionDTO.getToken().equals(session.getToken())) {
                    if (sessionDTO.getLoginDateTime().isBefore(
                            session.getLoginDateTime().plusHours(2))) {
                        result.set(Optional.of(SessionAdapter.transformDAOtoDTO(session)));
                    }
                }
            });
        }
        return result.get();
    }
}
