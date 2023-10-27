package fr.kragwu.muscletracker.userapi.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import fr.kragwu.muscletracker.userapi.adapters.SessionAdapter;
import fr.kragwu.muscletracker.userapi.adapters.UserAdapter;
import fr.kragwu.muscletracker.userapi.entities.Session;
import fr.kragwu.muscletracker.userapi.entities.User;
import fr.kragwu.muscletracker.userapi.repositories.SessionRepository;
import fr.kragwu.muscletracker.userapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.kragwu.muscletracker.userapi.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.dto.UserDTO;
import fr.kragwu.muscletracker.userapi.security.Cipher;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;
    //List<UserDTO> listUserDTOS = new ArrayList<>();
    //List<SessionDTO> listSessionDTOS = new ArrayList<>();

    public boolean registerUser(UserDTO userDTO) {
        Optional<UserDTO> optUser = getUser(userDTO);
        if (optUser.isEmpty()) {
            String currentDate = LocalDate.now().toString();
            String userLogin = Cipher.decrypt(userDTO.getLogin(), currentDate);
            userDTO.setLogin(userLogin);
            //listUserDTO.add(userDTO);
            userRepository.save(UserAdapter.transformDTOtoDAO(userDTO));
        }
        return optUser.isEmpty();
    }

    public Optional<UserDTO> getUser(UserDTO userDTO) {
        AtomicReference<Optional<UserDTO>> result = new AtomicReference<>(Optional.empty());
        if (userDTO != null && userDTO.getLogin() != null) {
            String currentDate = LocalDate.now().toString();
            String userLogin = Cipher.decrypt(userDTO.getLogin(), currentDate);
            Optional<User> optUser = userRepository.findByLogin(userLogin);
            optUser.ifPresentOrElse(user -> {
                        String userPassword = Cipher.decrypt(userDTO.getPassword(), currentDate);
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
            //listSessionDTOS.remove(sessionDTOFind);
            sessionDTOFind = sessionDTOFind.update(sessionDTO);
            sessionRepository.save(SessionAdapter.transformDTOtoDAO(sessionDTOFind));
            //listSessionDTOS.add(sessionDTO);
        }, () -> {
            System.out.println("Session not found");
            sessionRepository.save(SessionAdapter.transformDTOtoDAO(sessionDTO));
            //listSessionDTOS.add(sessionDTO);
        });
    }

    public void logoutSession(SessionDTO sessionDTO) {
        Optional<SessionDTO> optSessionDTO = getSession(sessionDTO);
        optSessionDTO.ifPresent(sessionDTOFind -> {
            sessionRepository.delete(SessionAdapter.transformDTOtoDAO(sessionDTOFind));
            //listSessionDTOS.remove(sessionDTOFind);
            sessionDTOFind = sessionDTOFind.update(sessionDTO);
            sessionRepository.save(SessionAdapter.transformDTOtoDAO(sessionDTOFind));
            //listSessionDTOS.add(sessionDTO);
        });
    }

    public Optional<SessionDTO> getSession(SessionDTO sessionDTO) {
        AtomicReference<Optional<SessionDTO>> result = new AtomicReference<>(Optional.empty());
        if (sessionDTO != null) {
            System.out.println("SessionDTO : " + sessionDTO.toString());
            if(sessionDTO.getIdUser() != null) {
                Optional<Session> optSession = sessionRepository.findByIdUser(sessionDTO.getIdUser());
                optSession.ifPresentOrElse(session -> result.set(Optional.of(SessionAdapter.transformDAOtoDTO(session))),
                        () -> System.out.println("Pas de session par IdUser"));
            }
            if(!result.get().isPresent() && sessionDTO.getId() != null) {
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
