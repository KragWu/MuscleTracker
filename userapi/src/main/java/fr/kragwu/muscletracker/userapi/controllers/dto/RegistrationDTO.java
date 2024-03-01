package fr.kragwu.muscletracker.userapi.controllers.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotNull;

@JsonDeserialize
public class RegistrationDTO {
    @NotNull
    String login;
    @NotNull
    String password;

    public RegistrationDTO() {
        super();
    }
    
    public RegistrationDTO(String login, String password) {
        super();
        this.login = login;
        this.password = password;
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

    @Override
    public String toString() {
        return "Registration(login = "+ this.login + 
        ", password = "+ this.password + ")";
    }
}
