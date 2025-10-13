package fr.kragwu.muscletracker.userapi.controllers.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonDeserialize
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    @NotNull
    String login;
    @NotNull
    String password;

    @Override
    public String toString() {
        return "RegistrationDTO(login = "+ this.login +
        ", password = "+ this.password + ")";
    }
}
