package fr.kragwu.muscletracker.userapi.utils;

import fr.kragwu.muscletracker.userapi.dto.SessionDTO;

public class SessionJSONParser {
    public static SessionDTO readJSON(SessionDTO sessionDTO) {
        return new SessionDTO(sessionDTO.getId(), null, null, null, sessionDTO.getToken());
    }
}
