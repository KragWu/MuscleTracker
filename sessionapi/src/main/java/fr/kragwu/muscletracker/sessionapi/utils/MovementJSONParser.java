package fr.kragwu.muscletracker.sessionapi.utils;

import fr.kragwu.muscletracker.sessionapi.dto.Movement;

public class MovementJSONParser {
    public static Movement readJson(Movement movement) {
        return new Movement(movement.getName(), movement.getRepetition(), movement.getWeight(), movement.getIdSession(), true);
    }
}
