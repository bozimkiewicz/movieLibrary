package pl.bozimkiewicz.movielibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bozimkiewicz.movielibrary.domain.Director;
import pl.bozimkiewicz.movielibrary.domain.Movie;

import java.util.*;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    List<Movie> findAllByWatched(Boolean watched);
    List<Movie> findAllByDirector_Id(UUID id);
    Optional<Movie> findByMovieDetails_Id(UUID id);

    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Movie> getRandomMovie();

    @Query("SELECT m FROM Movie m JOIN m.actors a WHERE a.firstName = ?1 AND a.lastName = ?2")
    List<Movie> findAllByActorFullName(String firstName, String lastName);

    @Query("SELECT m FROM Movie m JOIN m.director d WHERE d.firstName = ?1 AND d.lastName = ?2")
    List<Movie> findMoviesByDirectorFullName(String firstName, String lastName);
}
