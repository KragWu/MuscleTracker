package fr.kragwu.muscletracker.userapi.controllers;

import fr.kragwu.muscletracker.userapi.adapters.SessionAdapter;
import fr.kragwu.muscletracker.userapi.controllers.dto.RegistrationDTO;
import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.controllers.dto.StatusDTO;
import fr.kragwu.muscletracker.userapi.services.UserService;
import fr.kragwu.muscletracker.userapi.services.bo.SessionBO;
import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import fr.kragwu.muscletracker.userapi.services.exception.BadCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
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
    public Mono<ResponseEntity<StatusDTO>> login(@Validated @RequestBody RegistrationDTO payload) {
        return Mono.just(payload)
                .map(registrationDTO -> {
                    log.info("Login User : {}", registrationDTO);
                    return registrationDTO;
                })
                .map(
                        registerDTO -> UserBO.builder()
                                .login(registerDTO.getLogin())
                                .password(registerDTO.getPassword())
                                .build()
                )
                .map(
                        userBO -> {
                            SessionBO sessionBO = new SessionBO();
                            try {
                                userService.getUser(userBO).ifPresentOrElse(userBOFind -> {
                                    sessionBO.setId(UUID.randomUUID());
                                    sessionBO.setIdUser(userBOFind.getId());
                                    sessionBO.setLoginDateTime(LocalDateTime.now());
                                    sessionBO.setToken(UUID.randomUUID().toString());
                                    userService.registerSession(sessionBO);
                                }, () -> log.info("User not found"));
                            } catch (BadCredentialsException bce) {
                                log.error(bce.getMessage());
                            }
                            return sessionBO;
                        }
                )
                .map(SessionAdapter::transformBOtoDTO)
                .map(sessionDTO -> StringUtils.isEmpty(sessionDTO.getId()) ? ResponseEntity.status(401).body(new StatusDTO("Login failed")) :
                        ResponseEntity.status(200)
                                .header("session", sessionDTO.getId())
                                .body(new StatusDTO("Login succeed")));
    }

    @PostMapping(value = "/register")
    public Mono<ResponseEntity<StatusDTO>> register(@Validated @RequestBody RegistrationDTO payload) {
        return Mono.just(payload)
                .map(registrationDTO -> {
                    log.info("Register User : {}", registrationDTO);
                    return registrationDTO;
                })
                .map(registerDTO -> UserBO.builder()
                        .id(UUID.randomUUID())
                        .login(registerDTO.getLogin())
                        .password(registerDTO.getPassword())
                        .registrationDate(LocalDate.now())
                        .build())
                .map(userBO -> userService.registerUser(userBO) ? ResponseEntity.status(201).body(new StatusDTO("Registration succeed")) :
                        ResponseEntity.status(400).body(new StatusDTO("Registration failed")));
    }

    @GetMapping(value = "/token")
    public Mono<ResponseEntity<StatusDTO>> getToken(@RequestHeader(value = "session") String sessionHeader) {
        return Mono.just(sessionHeader)
                .map(sessionStr -> {
                    log.info("Session User : {}", sessionStr);
                    return sessionStr;
                })
                .map(sessionStr -> SessionDTO.builder()
                        .id(sessionStr)
                        .build())
                .map(SessionAdapter::transformDTOtoBO)
                .map(sessionBO -> userService.getSession(sessionBO).orElse(SessionBO.builder().build()))
                .map(SessionAdapter::transformBOtoDTO)
                .map(sessionDTO -> {
                    if (StringUtils.isBlank(sessionDTO.getId()) || StringUtils.isBlank(sessionDTO.getToken())) {
                        return ResponseEntity.status(401).body(new StatusDTO("Tokenization failed"));
                    } else {
                        return ResponseEntity.status(200)
                                .header("token", sessionDTO.getToken())
                                .body(new StatusDTO("Tokenization succeed"));
                    }
                });
    }

    @PostMapping(value = "/logout")
    public Mono<ResponseEntity<StatusDTO>> logout(@RequestHeader(value = "session") String sessionHeader,
                                                  @RequestHeader(value = "token") String tokenHeader) {
        return Mono.just(SessionDTO.builder().id(sessionHeader).token(tokenHeader).build())
                .map(sessionDTO -> {
                    log.info("Logout User : {}", sessionDTO.getId());
                    SessionBO sessionBO = SessionAdapter.transformDTOtoBO(sessionDTO);
                    sessionBO.setLogoutDateTime(LocalDateTime.now());
                    return sessionBO;
                })
                .map(sessionBO -> {
                    userService.logoutSession(sessionBO);
                    return ResponseEntity.status(200).body(new StatusDTO("Logout succeed"));
                });
    }

    @PostMapping(value = "/authorize")
    public Mono<ResponseEntity<StatusDTO>> authorize(@RequestHeader(value = "session") String sessionHeader,
                                                     @RequestHeader(value = "token") String tokenHeader) {
        return Mono.just(SessionDTO.builder().id(sessionHeader).token(tokenHeader).build())
                .map(sessionDTO -> {
                    log.info("Authorize User : {}", sessionDTO.getId());
                    SessionBO sessionBO = SessionAdapter.transformDTOtoBO(sessionDTO);
                    sessionBO.setLoginDateTime(LocalDateTime.now());
                    return sessionBO;
                })
                .map(userService::authorize)
                .map(optSessionBO -> optSessionBO.isPresent() ? ResponseEntity.status(200).body(new StatusDTO("Authorization succeed")) :
                        ResponseEntity.status(401).body(new StatusDTO("Authorization failed")));
    }
}
