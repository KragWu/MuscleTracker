package fr.kragwu.muscletracker.userapi.repositories;

import fr.kragwu.muscletracker.userapi.entities.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
    Optional<Session> findByIdUser(String idUser);
}
