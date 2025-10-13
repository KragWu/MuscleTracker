package fr.kragwu.muscletracker.userapi.services.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@JsonDeserialize
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBO {
    UUID id;
    String login;
    String password;
    LocalDate registrationDate;

    @Override
    public String toString() {
        return "UserBO(id = "+ this.id +
        ", login = "+ this.login + 
        ", password = "+ this.password + 
        ", registrationDate = "+ this.registrationDate + ")";
    }
}
