package fr.kragwu.muscletracker.sessionapi.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.kragwu.muscletracker.sessionapi.dto.Movement;
import fr.kragwu.muscletracker.sessionapi.dto.Session;
import fr.kragwu.muscletracker.sessionapi.utils.MovementJSONParser;

@CrossOrigin(origins = {"http://localhost:3030", "http://192.168.1.32:3030"})
@RestController
public class SessionController {
    
    @GetMapping(value = "/history")
    public ResponseEntity<List<Session>> history() {
        return ResponseEntity.status(501).build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("API Session started");
    }

    @PostMapping(value = "/movement", headers = {"Accept=application/json"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movement> storeMovement(@RequestBody Movement payload) {
        System.out.println(payload);
        Movement result = MovementJSONParser.readJson(payload);
        System.out.println(result);
        return ResponseEntity.status(201).body(result);
    }

    @PostMapping(value = "/startsession")
    public ResponseEntity<String> startSession() {
        return ResponseEntity.status(201).body("Start Session");
    }
}
