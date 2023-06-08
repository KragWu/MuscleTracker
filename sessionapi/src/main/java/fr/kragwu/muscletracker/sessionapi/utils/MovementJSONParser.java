package fr.kragwu.muscletracker.sessionapi.utils;

import fr.kragwu.muscletracker.sessionapi.dto.Movement;

public class MovementJSONParser {
    public static Movement readJson() {
        return new Movement("", 0, 0, "");
    }
}
