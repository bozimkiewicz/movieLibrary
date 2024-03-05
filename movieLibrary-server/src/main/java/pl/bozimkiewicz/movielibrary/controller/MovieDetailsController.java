package pl.bozimkiewicz.movielibrary.controller;

import org.springframework.web.bind.annotation.*;
import pl.bozimkiewicz.movielibrary.domain.MovieDetails;
import pl.bozimkiewicz.movielibrary.dto.MovieDetailsDTO;
import pl.bozimkiewicz.movielibrary.service.MovieDetailsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movie-details")
public class MovieDetailsController {
    private final MovieDetailsService movieDetailsService;

    public MovieDetailsController(MovieDetailsService movieDetailsService) {
        this.movieDetailsService = movieDetailsService;
    }

    @GetMapping("")
    public List<MovieDetailsDTO> getAllMovieDetails() {
        return movieDetailsService.getAllMovieDetails();
    }

    @GetMapping("/{id}")
    public MovieDetailsDTO getMovieDetailsById(@PathVariable UUID id) {
        return movieDetailsService.getMovieDetailsById(id);
    }

    @PostMapping("")
    public MovieDetails addMovieDetails(@RequestBody MovieDetailsDTO movieDetailsDTO) {
        return movieDetailsService.addMovieDetails(movieDetailsDTO);
    }

    @PutMapping("/{id}")
    public MovieDetails updateMovieDetails(@PathVariable UUID id, @RequestBody MovieDetailsDTO movieDetailsToUpdate) {
        return movieDetailsService.updateMovieDetails(id, movieDetailsToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieDetails(@PathVariable UUID id) {
        movieDetailsService.deleteMovieDetails(id);
    }

    @DeleteMapping("/all")
    public void deleteAllMovieDetails() {
        movieDetailsService.deleteAllMovieDetails();
    }
}
