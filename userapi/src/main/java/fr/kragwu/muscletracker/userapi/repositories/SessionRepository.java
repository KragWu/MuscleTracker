package fr.kragwu.muscletracker.userapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.kragwu.muscletracker.userapi.repositories.entities.Session;

import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
    Optional<Session> findByIdUser(String idUser);
}
