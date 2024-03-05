package pl.bozimkiewicz.movielibrary.controller;

import org.springframework.web.bind.annotation.*;
import pl.bozimkiewicz.movielibrary.domain.Movie;
import pl.bozimkiewicz.movielibrary.dto.MovieDTO;
import pl.bozimkiewicz.movielibrary.service.MovieService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieDTO getMovieById(@PathVariable UUID id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/random")
    public MovieDTO getRandomMovie() {
        return movieService.getRandomMovie();
    }

    @GetMapping("/watched")
    public List<MovieDTO> findAllByWatched(@RequestParam boolean watched) {
        return movieService.findAllByWatched(watched);
    }

    @GetMapping("/director")
    public List<MovieDTO> findAllByDirectorFullName(@RequestParam String firstName, @RequestParam String lastName) {
        return movieService.findAllByDirectorFullName(firstName, lastName);
    }

    @PostMapping("")
    public Movie addMovie(@RequestBody MovieDTO movieDTO) {
        return movieService.addMovie(movieDTO);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable UUID id, @RequestBody MovieDTO movieToUpdate) {
        return movieService.updateMovie(id, movieToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable UUID id) {
        movieService.deleteMovie(id);
    }

    @DeleteMapping("/all")
    public void deleteAllMovies() {
        movieService.deleteAllMovies();
    }
}
