package fr.kragwu.muscletracker.userapi.services.bo;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class UserBO {
    String id;
    String login;
    String password;
    LocalDate registrationDate;


    public UserBO() {
        super();
    }

    public UserBO(String id, String login, String password, LocalDate registrationDate) {
        super();
        this.id = id;
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    @Override
    public String toString() {
        return "UserDTO(id = "+ this.id +
        ", login = "+ this.login + 
        ", password = "+ this.password + 
        ", registrationDate = "+ this.registrationDate + ")";
    }
}
