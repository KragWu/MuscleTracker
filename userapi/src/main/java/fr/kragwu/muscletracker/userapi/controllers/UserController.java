package fr.kragwu.muscletracker.userapi.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.kragwu.muscletracker.userapi.dto.Registration;
import fr.kragwu.muscletracker.userapi.dto.Session;
import fr.kragwu.muscletracker.userapi.dto.User;
import fr.kragwu.muscletracker.userapi.services.UserService;
import fr.kragwu.muscletracker.userapi.utils.SessionJSONParser;
import fr.kragwu.muscletracker.userapi.utils.RegistrationJSONParser;

@CrossOrigin(origins = {"http://localhost:3030", "http://192.168.1.6:3030"})
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("API User started");
    }

    @PostMapping(value = "/login", headers = {"Accept=application/json"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Session> login(@RequestBody Registration payload) {
        System.out.println("Login User : " + payload.getLogin());
        Registration register = RegistrationJSONParser.readJSON(payload);
        User user = new User();
        Session session = new Session();
        user.setLogin(register.getLogin());
        user.setPassword(register.getPassword());
        userService.getUser(user).ifPresentOrElse(userFind -> {
            session.setId(UUID.randomUUID().toString());
            session.setIdUser(userFind.getId());
            session.setLoginDateTime(LocalDateTime.now());
            session.setToken(UUID.randomUUID().toString());
            userService.registerSession(session);
        }, () -> session.setId(""));
        
        System.out.println(session);
        return session.getId().isEmpty() ? ResponseEntity.status(400).body(null) :
            ResponseEntity.status(200).body(session);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody Registration payload) {
        System.out.println("Register User : " + payload.getLogin());
        Registration register = RegistrationJSONParser.readJSON(payload);
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setLogin(register.getLogin());
        user.setPassword(register.getPassword());
        user.setRegistrationDate(LocalDate.now());
    
        return userService.registerUser(user) ? ResponseEntity.status(201).body("OK") : 
           ResponseEntity.status(400).body("K0");
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody Session payload) {
        System.out.println("Logout User : " + payload.getId());
        Session session = SessionJSONParser.readJSON(payload);
        session.setLogoutDateTime(LocalDateTime.now());
        userService.logoutSession(session);
        return ResponseEntity.status(200).body("OK");
    }

    @PostMapping(value = "/authorize")
    public ResponseEntity<String> authorize(@RequestBody Session payload) {
        System.out.println("Authorize User : " + payload.getId());
        Session session = SessionJSONParser.readJSON(payload);
        Optional<Session> result = userService.authorize(session);
        return result.isPresent() ? ResponseEntity.status(200).body("OK") : 
            ResponseEntity.status(401).body("Unauthorized");
    }
}
