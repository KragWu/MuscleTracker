package fr.kragwu.muscletracker.userapi.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.kragwu.muscletracker.userapi.dto.Session;
import fr.kragwu.muscletracker.userapi.dto.User;
import fr.kragwu.muscletracker.userapi.security.Cipher;

@Service
public class UserService {
    
    List<User> listUsers = new ArrayList<>();
    List<Session> listSessions = new ArrayList<>();

    public boolean registerUser(User user) {
        Optional<User> optUser = getUser(user);
        if (!optUser.isPresent()) {
            String currentDate = LocalDate.now().toString();
            String userLogin = Cipher.decrypt(user.getLogin(), currentDate);
            user.setLogin(userLogin);
            listUsers.add(user);
        }
        return !optUser.isPresent();
    }

    public Optional<User> getUser(User user) {
        Optional<User> result = Optional.empty();
        if (user != null && user.getLogin() != null) {
            String currentDate = LocalDate.now().toString();
            String userLogin = Cipher.decrypt(user.getLogin(), currentDate);
            for (User userList : listUsers) {
                if (userLogin.equals(userList.getLogin())) {
                    String userPassword = Cipher.decrypt(user.getPassword(), currentDate);
                    String userListPassword = Cipher.decrypt(userList.getPassword(), userList.getRegistrationDate().toString());
                    if (userPassword.equals(userListPassword)) {
                        result = Optional.of(userList);
                    }
                }
            }
        }
        return result;
    }

    public void registerSession(Session session) {
        Optional<Session> optSession = getSession(session);
        optSession.ifPresentOrElse(sessionFind -> {
            listSessions.remove(sessionFind);
            sessionFind.update(session);
            listSessions.add(session);
        }, () -> listSessions.add(session));
    }

    public void logoutSession(Session session) {
        Optional<Session> optSession = getSession(session);
        optSession.ifPresent(sessionFind -> {
            listSessions.remove(sessionFind);
            sessionFind.update(session);
            sessionFind.setToken(null);
            listSessions.add(session);
        });
    }

    public Optional<Session> getSession(Session session) {
        Optional<Session> result = Optional.empty();
        if (session != null && session.getIdUser() != null) {
            for (Session sessionList : listSessions) {
                if (session.getIdUser().equals(sessionList.getIdUser())) {
                    result = Optional.of(sessionList);
                }
            }
        }
        return result;
    }

    public Optional<Session> authorize(Session session) {
        Optional<Session> result = Optional.empty();
        if (session != null && session.getId() != null && session.getToken() != null) {
            for (Session sessionList : listSessions) {
                if (session.getId().equals(sessionList.getId()) && 
                    session.getToken().equals(sessionList.getToken())) {
                    if (session.getLoginDateTime().compareTo(
                        sessionList.getLoginDateTime().plusHours(2)) < 0) {
                        result = Optional.of(sessionList);
                    }
                }
            }
        }
        return result;
    }
}
