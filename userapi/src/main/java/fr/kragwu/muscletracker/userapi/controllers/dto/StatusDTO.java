package fr.kragwu.muscletracker.userapi.controllers.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class StatusDTO {
    String message;

    public StatusDTO() {
        super();
    }

    public StatusDTO(String message) {
        super();
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "Status(message = "+ this.message + ")";
    }
}
