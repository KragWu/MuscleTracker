package fr.kragwu.muscletracker.userapi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class SessionDTO {
    String id;
    String idUser;
    LocalDateTime loginDateTime;
    LocalDateTime logoutDateTime;
    String token;


    public SessionDTO() {
        super();
    }

    public SessionDTO(String id, String idUser, LocalDateTime loginDateTime, LocalDateTime logoutDateTime, String token) {
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

    public SessionDTO update(SessionDTO sessionDTO) {
        SessionDTO result = new SessionDTO();
        if (sessionDTO.getId() == null || sessionDTO.getId().isEmpty()) {
            System.out.println("Update Id by BDD");
            result.setId(this.id);
        } else {
            result.setId(sessionDTO.getId());
        }
        if (sessionDTO.getIdUser() == null || sessionDTO.getIdUser().isEmpty()) {
            System.out.println("Update IdUser by BDD");
            result.setIdUser(this.idUser);
        } else {
            result.setIdUser(sessionDTO.getIdUser());
        }
        if (sessionDTO.getLoginDateTime() == null) {
            System.out.println("Update LoginDate by BDD");
            result.setLoginDateTime(this.loginDateTime);
        } else {
            result.setLoginDateTime(sessionDTO.getLoginDateTime());
        }
        if (sessionDTO.getLogoutDateTime() == null) {
            System.out.println("Update LogoutDate by BDD");
            result.setLogoutDateTime(this.logoutDateTime);
        } else {
            result.setLogoutDateTime(sessionDTO.getLogoutDateTime());
        }
        if (sessionDTO.getToken() == null || sessionDTO.getToken().isEmpty()) {
            System.out.println("Update Token by BDD");
            result.setToken(this.token);
        } else {
            result.setToken(sessionDTO.getToken());
        }
        return result;
    }

    @Override
    public String toString() {
        return "SessionDTO(id = "+ this.id +
        ", idUser = "+ this.idUser + 
        ", loginDateTime = "+ this.loginDateTime + 
        ", logoutDateTime = "+ this.logoutDateTime + 
        ", token = "+ this.token + ")";
    }
}
