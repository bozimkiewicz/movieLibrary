package pl.bozimkiewicz.movielibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bozimkiewicz.movielibrary.domain.Actor;

import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<Actor, UUID> {
}
