package fr.kragwu.muscletracker.userapi.controllers.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonDeserialize
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    String message;

    @Override
    public String toString() {
        return "StatusDTO(message = "+ this.message + ")";
    }
}
