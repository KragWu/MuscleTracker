package fr.kragwu.muscletracker.userapi.services.bo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionBO {
    @NotNull
    UUID id;
    UUID idUser;
    LocalDateTime loginDateTime;
    LocalDateTime logoutDateTime;
    @NotNull
    String token;

    public SessionBO update(SessionBO sessionBO) {
        SessionBO result = new SessionBO();
        if (sessionBO.getId() == null) {
            result.setId(this.id);
        } else {
            result.setId(sessionBO.getId());
        }
        if (sessionBO.getIdUser() == null) {
            result.setIdUser(this.idUser);
        } else {
            result.setIdUser(sessionBO.getIdUser());
        }
        if (sessionBO.getLoginDateTime() == null) {
            result.setLoginDateTime(this.loginDateTime);
        } else {
            result.setLoginDateTime(sessionBO.getLoginDateTime());
        }
        if (sessionBO.getLogoutDateTime() == null) {
            result.setLogoutDateTime(this.logoutDateTime);
        } else {
            result.setLogoutDateTime(sessionBO.getLogoutDateTime());
        }
        if (sessionBO.getToken() == null || sessionBO.getToken().isEmpty()) {
            result.setToken(this.token);
        } else {
            result.setToken(sessionBO.getToken());
        }
        return result;
    }

    @Override
    public String toString() {
        return "SessionBO(id = "+ this.id +
                ", idUser = "+ this.idUser +
                ", loginDateTime = "+ this.loginDateTime +
                ", logoutDateTime = "+ this.logoutDateTime +
                ", token = "+ this.token + ")";
    }
}
