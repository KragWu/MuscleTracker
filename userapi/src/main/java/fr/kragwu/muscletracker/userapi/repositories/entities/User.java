package fr.kragwu.muscletracker.userapi.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "utilisateur", schema = "client")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    String id;
    @Column(name = "login", unique = true, nullable = false)
    String login;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "registrationDate", nullable = false)
    LocalDate registrationDate;

    public User() {
        super();
    }

    public User(String id, String login, String password, LocalDate registrationDate) {
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
        return "User(id = "+ this.id +
                ", login = "+ this.login +
                ", password = "+ this.password +
                ", registrationDate = "+ this.registrationDate + ")";
    }
}
