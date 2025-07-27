package fr.kragwu.muscletracker.userapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.kragwu.muscletracker.userapi.repositories.entities.Session;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {
    Optional<Session> findByIdUser(UUID idUser);
}
