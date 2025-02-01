package fr.kragwu.muscletracker.userapi.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.kragwu.muscletracker.userapi.controllers.dto.RegistrationDTO;
import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.controllers.dto.StatusDTO;
import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import fr.kragwu.muscletracker.userapi.services.UserService;
import fr.kragwu.muscletracker.userapi.utils.SessionJSONParser;
import reactor.core.publisher.Mono;
import fr.kragwu.muscletracker.userapi.utils.RegistrationJSONParser;

@CrossOrigin(origins = {"http://localhost:3030", "http://192.168.1.6:3030", "http://localhost:4200"})
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public Mono<ResponseEntity<String>> hello() {
        return Mono.just(ResponseEntity.ok().body("API User started"));
    }

    @PostMapping(value = "/login", headers = {"Accept=application/json"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<SessionDTO>> login(@Validated @RequestBody RegistrationDTO payload) {
        return Mono.just(payload)
            .map( it -> {
                System.out.println("Login User : " + it);
                return RegistrationJSONParser.readJSON(it);
            })
            .map(
                registerDTO -> {
                    UserBO userBO = new UserBO();
                    userBO.setLogin(registerDTO.getLogin());
                    userBO.setPassword(registerDTO.getPassword());
                    return userBO;
                }
            )
            .map(
                    userBO -> {
                    SessionDTO sessionDTO = new SessionDTO();
                    userService.getUser(userBO).ifPresentOrElse(userBOFind -> {
                        sessionDTO.setId(UUID.randomUUID().toString());
                        sessionDTO.setIdUser(userBOFind.getId());
                        sessionDTO.setLoginDateTime(LocalDateTime.now());
                        sessionDTO.setToken(UUID.randomUUID().toString());
                        userService.registerSession(sessionDTO);
                    }, () -> sessionDTO.setId(""));
                    return sessionDTO;
                }
            )
            .map(sessionDTO -> sessionDTO.getId().isEmpty() ? ResponseEntity.status(401).body(null) :
                 ResponseEntity.status(200).body(sessionDTO));
    }

    @PostMapping(value = "/register")
    public Mono<ResponseEntity<StatusDTO>> register(@Validated @RequestBody RegistrationDTO payload) {
        return Mono.just(payload)
            .map(it -> {
                System.out.println("Register User : " + it);
                return RegistrationJSONParser.readJSON(it);
            })
            .map(registerDTO -> {
                UserBO userBO = new UserBO();
                userBO.setId(UUID.randomUUID().toString());
                userBO.setLogin(registerDTO.getLogin());
                userBO.setPassword(registerDTO.getPassword());
                userBO.setRegistrationDate(LocalDate.now());
                return userBO;
            })
            .map(userBO -> userService.registerUser(userBO) ? ResponseEntity.status(201).body(new StatusDTO("OK")) :
                ResponseEntity.status(400).body(null));
    }

    @PostMapping(value = "/logout")
    public Mono<ResponseEntity<StatusDTO>> logout(@Validated @RequestBody SessionDTO payload) {
        return Mono.just(payload)
            .map(it -> {
                System.out.println("Logout User : " + it.getId());
                SessionDTO sessionDTO = SessionJSONParser.readJSON(it);
                sessionDTO.setLogoutDateTime(LocalDateTime.now());
                return sessionDTO;
            })
            .map(sessionDTO -> {
                userService.logoutSession(sessionDTO);
                return ResponseEntity.status(200).body(new StatusDTO("OK"));
            });
    }

    @PostMapping(value = "/authorize")
    public Mono<ResponseEntity<StatusDTO>> authorize(@Validated @RequestBody SessionDTO payload) {
        return Mono.just(payload)
            .map(it -> {
                System.out.println("Authorize User : " + it.getId());
                SessionDTO sessionDTO = SessionJSONParser.readJSON(it);
                sessionDTO.setLoginDateTime(LocalDateTime.now());
                return sessionDTO;
            })
            .map(userService::authorize)
            .map(optSessionDTO -> optSessionDTO.isPresent() ? ResponseEntity.status(200).body(new StatusDTO("OK")) :
                ResponseEntity.status(401).body(null));
    }
}
