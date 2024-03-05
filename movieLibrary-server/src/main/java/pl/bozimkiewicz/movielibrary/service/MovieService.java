package pl.bozimkiewicz.movielibrary.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.bozimkiewicz.movielibrary.domain.Actor;
import pl.bozimkiewicz.movielibrary.domain.Director;
import pl.bozimkiewicz.movielibrary.domain.Movie;
import pl.bozimkiewicz.movielibrary.domain.MovieDetails;
import pl.bozimkiewicz.movielibrary.dto.MovieDTO;
import pl.bozimkiewicz.movielibrary.repository.ActorRepository;
import pl.bozimkiewicz.movielibrary.repository.DirectorRepository;
import pl.bozimkiewicz.movielibrary.repository.MovieDetailsRepository;
import pl.bozimkiewicz.movielibrary.repository.MovieRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final MovieDetailsRepository movieDetailsRepository;

    public MovieService(
            MovieRepository movieRepository,
            DirectorRepository directorRepository,
            ActorRepository actorRepository,
            MovieDetailsRepository movieDetailsRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
        this.movieDetailsRepository = movieDetailsRepository;
    }

    public List<MovieDTO> getAllMovies() {
        final List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> mapToDTO(movie, new MovieDTO())).toList();
    }

    public MovieDTO getMovieById(UUID id) {
        return movieRepository.findById(id)
                .map(movie -> mapToDTO(movie, new MovieDTO()))
                .orElseThrow(() -> new RuntimeException("Movie with id " + id + " not found"));
    }

    public MovieDTO getRandomMovie() {
        return movieRepository.getRandomMovie()
                .map(movie -> mapToDTO(movie, new MovieDTO()))
                .orElseThrow(() -> new RuntimeException("No movies found"));
    }

    public List<MovieDTO> findAllByWatched(Boolean watched) {
        final List<Movie> movies = movieRepository.findAllByWatched(watched);
        return movies.stream()
                .map(movie -> mapToDTO(movie, new MovieDTO()))
                .toList();
    }

    public List<MovieDTO> findAllByDirectorFullName(String firstName, String lastName) {
        final List<Movie> movies = movieRepository.findMoviesByDirectorFullName(firstName, lastName);
        return movies.stream()
                .map(movie -> mapToDTO(movie, new MovieDTO()))
                .toList();
    }

    public Movie addMovie(MovieDTO movieToSave) {
        final Movie movie = new Movie();
        mapToEntity(movieToSave, movie);
        return movieRepository.save(movie);
    }

    public Movie updateMovie(UUID id, MovieDTO movieToUpdate) {
        final Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie with id " + id + " not found"));
        if (movieToUpdate.getTitle() == null) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if (movieToUpdate.getWatched() == null) {
            movieToUpdate.setWatched(movie.getWatched());
        }
        if (movieToUpdate.getDirector() == null) {
            movieToUpdate.setDirector(movie.getDirector() == null ? null : movie.getDirector().getId());
        }
        if (movieToUpdate.getActors() == null) {
            movieToUpdate.setActors(movie.getActors().stream()
                    .map(Actor::getId)
                    .toList());
        }
        if (movieToUpdate.getMovieDetails() == null) {
            movieToUpdate.setMovieDetails(movie.getMovieDetails() == null ? null : movie.getMovieDetails().getId());
        }

        mapToEntity(movieToUpdate, movie);
        return movieRepository.save(movie);
    }

    public void deleteMovie(UUID id) {
        movieRepository.deleteById(id);
    }

    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }

    private MovieDTO mapToDTO(final Movie movie, final MovieDTO movieDTO) {
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setWatched(movie.getWatched());
        movieDTO.setDirector(movie.getDirector() == null ? null : movie.getDirector().getId());
        movieDTO.setActors(movie.getActors().stream()
                .map(Actor::getId)
                .toList());
        movieDTO.setMovieDetails(movie.getMovieDetails() == null ? null : movie.getMovieDetails().getId());
        return movieDTO;
    }

    private void mapToEntity(final MovieDTO movieDTO, final Movie movie) {
        movie.setTitle(movieDTO.getTitle());
        movie.setWatched(movieDTO.getWatched());

        final Director director = movieDTO
                .getDirector() == null ? null :
                    directorRepository.findById(movieDTO.getDirector())
                        .orElseThrow(() -> new RuntimeException("Director not found"));
        movie.setDirector(director);

        final List<Actor> actors = actorRepository.findAllById(
                movieDTO.getActors() == null ? Collections.emptyList() : movieDTO.getActors());
        if (actors.size() != (movieDTO.getActors() == null ? 0 : movieDTO.getActors().size())) {
            throw new RuntimeException("One of actors not found");
        }
        movie.setActors(actors);

        final MovieDetails movieDetails = movieDTO
                .getMovieDetails() == null ? null :
                    movieDetailsRepository.findById(movieDTO.getMovieDetails())
                        .orElseThrow(() -> new RuntimeException("MovieDetails not found"));
        movie.setMovieDetails(movieDetails);
    }
}
