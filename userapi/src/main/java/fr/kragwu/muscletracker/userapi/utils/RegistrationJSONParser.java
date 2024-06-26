package fr.kragwu.muscletracker.userapi.utils;

import fr.kragwu.muscletracker.userapi.controllers.dto.RegistrationDTO;

public class RegistrationJSONParser {
    public static RegistrationDTO readJSON(RegistrationDTO registrationDTO) {
        return new RegistrationDTO(registrationDTO.getLogin(), registrationDTO.getPassword());
    }
}
