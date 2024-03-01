package fr.kragwu.muscletracker.userapi.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "session", schema = "client")
public class Session {

    @Id
    @Column(name = "id", nullable = false)
    String id;
    @Column(name = "idUser", nullable = false)
    String idUser;
    @Column(name = "loginDate", nullable = false)
    LocalDateTime loginDateTime;
    @Column(name = "logoutDate")
    LocalDateTime logoutDateTime;
    @Column(name = "token", nullable = false)
    String token;

    public Session() {
        super();
    }

    public Session(String id, String idUser, LocalDateTime loginDateTime, LocalDateTime logoutDateTime, String token) {
        super();
        this.id = id;
        this.idUser = idUser;
        this.loginDateTime = loginDateTime;
        this.logoutDateTime = logoutDateTime;
        this.token = token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return this.idUser;
    }

    public void setLoginDateTime(LocalDateTime loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public LocalDateTime getLoginDateTime() {
        return this.loginDateTime;
    }

    public void setLogoutDateTime(LocalDateTime logoutDateTime) {
        this.logoutDateTime = logoutDateTime;
    }

    public LocalDateTime getLogoutDateTime() {
        return this.logoutDateTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public Session update(Session session) {
        Session result = new Session();
        if (session.getId() == null || session.getId().isEmpty()) {
            result.setId(this.id);
        } else {
            result.setId(session.getId());
        }
        if (session.getIdUser() == null || session.getIdUser().isEmpty()) {
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
