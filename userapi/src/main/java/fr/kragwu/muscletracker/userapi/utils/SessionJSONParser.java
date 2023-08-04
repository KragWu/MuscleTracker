package fr.kragwu.muscletracker.userapi.utils;

import fr.kragwu.muscletracker.userapi.dto.Session;

public class SessionJSONParser {
    public static Session readJSON(Session session) {
        return new Session(session.getId(), null, null, null, session.getToken());
    }
}
