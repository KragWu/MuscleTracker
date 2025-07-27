package fr.kragwu.muscletracker.userapi.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "utilisateur", schema = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id", nullable = false)
    UUID id;
    @Column(name = "login", unique = true, nullable = false)
    String login;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "registrationDate", nullable = false)
    LocalDate registrationDate;

    @Override
    public String toString() {
        return "User(id = "+ this.id +
                ", login = "+ this.login +
                ", password = "+ this.password +
                ", registrationDate = "+ this.registrationDate + ")";
    }
}
