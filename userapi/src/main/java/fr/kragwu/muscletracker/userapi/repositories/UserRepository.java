package fr.kragwu.muscletracker.userapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.kragwu.muscletracker.userapi.repositories.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByLogin(String login);
}
