package fr.kragwu.muscletracker.userapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class Registration {
    String login;
    String password;

    public Registration() {
        super();
    }
    
    public Registration(String login, String password) {
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
