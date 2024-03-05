package pl.bozimkiewicz.movielibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bozimkiewicz.movielibrary.domain.MovieDetails;

import java.util.UUID;

@Repository
public interface MovieDetailsRepository extends JpaRepository<MovieDetails, UUID> {
}
