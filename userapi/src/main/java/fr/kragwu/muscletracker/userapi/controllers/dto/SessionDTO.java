package fr.kragwu.muscletracker.userapi.controllers.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@JsonDeserialize
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    @NotNull
    String id;
    @NotNull
    String token;

    @Override
    public String toString() {
        return "SessionDTO(id = "+ this.id +
        ", token = "+ this.token + ")";
    }
}
