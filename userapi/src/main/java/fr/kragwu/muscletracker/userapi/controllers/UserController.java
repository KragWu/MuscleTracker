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

import fr.kragwu.muscletracker.userapi.dto.RegistrationDTO;
import fr.kragwu.muscletracker.userapi.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.dto.UserDTO;
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
    public ResponseEntity<SessionDTO> login(@RequestBody RegistrationDTO payload) {
        System.out.println("Login User : " + payload);
        RegistrationDTO register = RegistrationJSONParser.readJSON(payload);
        UserDTO userDTO = new UserDTO();
        SessionDTO sessionDTO = new SessionDTO();
        userDTO.setLogin(register.getLogin());
        userDTO.setPassword(register.getPassword());
        userService.getUser(userDTO).ifPresentOrElse(userDTOFind -> {
            sessionDTO.setId(UUID.randomUUID().toString());
            sessionDTO.setIdUser(userDTOFind.getId());
            sessionDTO.setLoginDateTime(LocalDateTime.now());
            sessionDTO.setToken(UUID.randomUUID().toString());
            userService.registerSession(sessionDTO);
        }, () -> sessionDTO.setId(""));
        
        System.out.println(sessionDTO);
        return sessionDTO.getId().isEmpty() ? ResponseEntity.status(400).body(null) :
            ResponseEntity.status(200).body(sessionDTO);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO payload) {
        System.out.println("Register User : " + payload);
        RegistrationDTO register = RegistrationJSONParser.readJSON(payload);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(UUID.randomUUID().toString());
        userDTO.setLogin(register.getLogin());
        userDTO.setPassword(register.getPassword());
        userDTO.setRegistrationDate(LocalDate.now());
    
        return userService.registerUser(userDTO) ? ResponseEntity.status(201).body("OK") :
           ResponseEntity.status(400).body("K0");
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody SessionDTO payload) {
        System.out.println("Logout User : " + payload.getId());
        SessionDTO sessionDTO = SessionJSONParser.readJSON(payload);
        sessionDTO.setLogoutDateTime(LocalDateTime.now());
        userService.logoutSession(sessionDTO);
        return ResponseEntity.status(200).body("OK");
    }

    @PostMapping(value = "/authorize")
    public ResponseEntity<String> authorize(@RequestBody SessionDTO payload) {
        System.out.println("Authorize User : " + payload.getId());
        SessionDTO sessionDTO = SessionJSONParser.readJSON(payload);
        sessionDTO.setLoginDateTime(LocalDateTime.now());
        Optional<SessionDTO> result = userService.authorize(sessionDTO);
        return result.isPresent() ? ResponseEntity.status(200).body("OK") : 
            ResponseEntity.status(401).body("Unauthorized");
    }
}
