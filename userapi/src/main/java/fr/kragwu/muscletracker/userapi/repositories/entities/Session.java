package fr.kragwu.muscletracker.userapi.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "session", schema = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @Column(name = "id", nullable = false)
    UUID id;
    @Column(name = "idUser", nullable = false)
    UUID idUser;
    @Column(name = "loginDate", nullable = false)
    LocalDateTime loginDateTime;
    @Column(name = "logoutDate")
    LocalDateTime logoutDateTime;
    @Column(name = "token", nullable = false)
    String token;

    public Session update(Session session) {
        Session result = new Session();
        if (session.getId() == null) {
            result.setId(this.id);
        } else {
            result.setId(session.getId());
        }
        if (session.getIdUser() == null) {
            result.setIdUser(this.idUser);
        } else {
            result.setIdUser(session.getIdUser());
        }
        if (session.getLoginDateTime() == null) {
            result.setLoginDateTime(this.loginDateTime);
        } else {
            result.setLoginDateTime(session.getLoginDateTime());
        }
        if (session.getLogoutDateTime() == null) {
            result.setLogoutDateTime(this.logoutDateTime);
        } else {
            result.setLogoutDateTime(session.getLogoutDateTime());
        }
        if (session.getToken() == null || session.getToken().isEmpty()) {
            result.setToken(this.token);
        } else {
            result.setToken(session.getToken());
        }
        return result;
    }

    @Override
    public String toString() {
        return "Session(id = "+ this.id +
                ", idUser = "+ this.idUser +
                ", loginDateTime = "+ this.loginDateTime +
                ", logoutDateTime = "+ this.logoutDateTime +
                ", token = "+ this.token + ")";
    }
}
