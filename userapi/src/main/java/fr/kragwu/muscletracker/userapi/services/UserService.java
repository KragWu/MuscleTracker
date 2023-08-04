package fr.kragwu.muscletracker.userapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.kragwu.muscletracker.userapi.dto.Session;
import fr.kragwu.muscletracker.userapi.dto.User;

@Service
public class UserService {
    
    List<User> listUsers = new ArrayList<>();
    List<Session> listSessions = new ArrayList<>();

    public boolean registerUser(User user) {
        Optional<User> optUser = getUser(user);
        if (!optUser.isPresent()) {
            listUsers.add(user);
        }
        System.out.println(!optUser.isPresent());
        return !optUser.isPresent();
    }

    public Optional<User> getUser(User user) {
        Optional<User> result = Optional.empty();
        if (user != null && user.getLogin() != null && user.getPassword() != null) {
            for (User userList : listUsers) {
                if (user.getLogin().equals(userList.getLogin()) && user.getPassword().equals(userList.getPassword())) {
                    result = Optional.of(userList);
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
        if (session != null && session.getId() != null) {
            for (Session sessionList : listSessions) {
                if (session.getId().equals(sessionList.getId())) {
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
                    result = Optional.of(sessionList);
                }
            }
        }
        return result;
    }
}
