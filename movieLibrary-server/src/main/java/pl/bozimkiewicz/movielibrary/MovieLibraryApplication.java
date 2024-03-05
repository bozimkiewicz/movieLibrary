package pl.bozimkiewicz.movielibrary;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.bozimkiewicz.movielibrary.domain.Actor;
import pl.bozimkiewicz.movielibrary.domain.Director;
import pl.bozimkiewicz.movielibrary.domain.Movie;
import pl.bozimkiewicz.movielibrary.domain.MovieDetails;
import pl.bozimkiewicz.movielibrary.repository.ActorRepository;
import pl.bozimkiewicz.movielibrary.repository.DirectorRepository;
import pl.bozimkiewicz.movielibrary.repository.MovieDetailsRepository;
import pl.bozimkiewicz.movielibrary.repository.MovieRepository;

import java.time.Duration;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MovieLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieLibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpApp(
            MovieRepository movieRepository,
            MovieDetailsRepository movieDetailsRepository,
            DirectorRepository directorRepository,
            ActorRepository actorRepository
    ) {
        return args -> {
            List<Director> directors = new ArrayList<>();
            List<MovieDetails> movieDetails = new ArrayList<>();
            List<Actor> actors = new ArrayList<>();
            List<Movie> movies = new ArrayList<>();


            directors.add(new Director("Quentin", "Tarantino"));
            directors.add(new Director("Christopher", "Nolan"));

            movieDetails.add(new MovieDetails(
                    """
                    Pulp Fiction is a 1994 neo-noir film about the lives of two mob hit men, a boxer,\s
                    a crime boss and his wife, and a pair of diner bandits that intertwine in four tales\s
                    of violence and redemption.
                    """,
                    "",
                    Duration.ofMinutes(154),
                    Year.of(1994),
                    "Crime",
                    "USA",
                    8.9));

            movieDetails.add(new MovieDetails(
                    """
                    Inception is a 2010 science fiction action film about a thief who steals corporate secrets\s
                    through the use of dream-sharing technology and is given the inverse task of planting an idea\s
                    into the mind of a C.E.O.
                    """,
                    "",
                    Duration.ofMinutes(148),
                    Year.of(2010),
                    "Action",
                    "USA",
                    8.8));

            actors.add(new Actor("John", "Travolta"));
            actors.add(new Actor("Samuel", "L. Jackson"));
            actors.add(new Actor("Uma", "Thurman"));
            actors.add(new Actor("Leonardo", "DiCaprio"));
            actors.add(new Actor("Joseph", "Gordon-Levitt"));

            movies.add(new Movie(
                    "Pulp Fiction",
                    true));

            movies.add(new Movie(
                    "Inception",
                    false));

            directorRepository.saveAll(directors);
            movieDetailsRepository.saveAll(movieDetails);
            actorRepository.saveAll(actors);

            movies.get(0).setDirector(directors.get(0));
            movies.get(0).setMovieDetails(movieDetails.get(0));
            movies.get(0).setActors(actors.subList(0, 3));

            movies.get(1).setDirector(directors.get(1));
            movies.get(1).setMovieDetails(movieDetails.get(1));
            movies.get(1).setActors(actors.subList(3, 5));

            movieRepository.saveAll(movies);

        };
    }

}
