package fr.kragwu.muscletracker.userapi.controllers;

import fr.kragwu.muscletracker.userapi.controllers.dto.StatusDTO;
import fr.kragwu.muscletracker.userapi.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

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
    @GetMapping(value = "/me")
    public Mono<ResponseEntity<Map<String, Object>>> me(@AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        if (jwt == null) {
            return Mono.just(ResponseEntity.status(401).body(Map.of("error", "unauthenticated")));
        }
        Map<String, Object> body = Map.of(
                "sub", jwt.getSubject(),
                "claims", jwt.getClaims()
        );
        return Mono.just(ResponseEntity.ok(body));
    }
}
