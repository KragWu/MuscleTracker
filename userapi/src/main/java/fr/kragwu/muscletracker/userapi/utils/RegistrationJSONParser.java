package fr.kragwu.muscletracker.userapi.utils;

import fr.kragwu.muscletracker.userapi.dto.Registration;

public class RegistrationJSONParser {
    public static Registration readJSON(Registration registration) {
        return new Registration(registration.getLogin(), registration.getPassword());
    }
}
