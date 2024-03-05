package pl.bozimkiewicz.movielibrary.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.bozimkiewicz.movielibrary.domain.MovieDetails;
import pl.bozimkiewicz.movielibrary.dto.MovieDetailsDTO;
import pl.bozimkiewicz.movielibrary.repository.MovieDetailsRepository;
import pl.bozimkiewicz.movielibrary.repository.MovieRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MovieDetailsService {
    private final MovieDetailsRepository movieDetailsRepository;
    private final MovieRepository movieRepository;

    public MovieDetailsService(MovieDetailsRepository movieDetailsRepository, MovieRepository movieRepository) {
        this.movieDetailsRepository = movieDetailsRepository;
        this.movieRepository = movieRepository;
    }

    public List<MovieDetailsDTO> getAllMovieDetails() {
        final List<MovieDetails> newMovieDetails = movieDetailsRepository.findAll();
        return newMovieDetails.stream()
                .map(movieDetails -> mapToDTO(movieDetails, new MovieDetailsDTO()))
                .toList();
    }

    public MovieDetailsDTO getMovieDetailsById(UUID id) {
        return movieDetailsRepository.findById(id)
                .map(movieDetails -> mapToDTO(movieDetails, new MovieDetailsDTO()))
                .orElseThrow(() -> new RuntimeException("MovieDetails with id " + id + " not found"));
    }

    public MovieDetails addMovieDetails(MovieDetailsDTO movieDetailsDTO) {
        final MovieDetails movieDetails = new MovieDetails();
        mapToEntity(movieDetailsDTO, movieDetails);
        return movieDetailsRepository.save(movieDetails);
    }

    public MovieDetails updateMovieDetails(UUID id, MovieDetailsDTO movieDetailsToUpdate) {
        final MovieDetails movieDetails = movieDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MovieDetails with id " + id + " not found"));
        if (movieDetailsToUpdate.getDescription() == null) {
            movieDetailsToUpdate.setDescription(movieDetails.getDescription());
        }
        if (movieDetailsToUpdate.getPoster() == null) {
            movieDetailsToUpdate.setPoster(movieDetails.getPoster());
        }
        if (movieDetailsToUpdate.getDuration() == null) {
            movieDetailsToUpdate.setDuration(movieDetails.getDuration());
        }
        if (movieDetailsToUpdate.getReleaseYear() == null) {
            movieDetailsToUpdate.setReleaseYear(movieDetails.getReleaseYear());
        }
        if (movieDetailsToUpdate.getGenre() == null) {
            movieDetailsToUpdate.setGenre(movieDetails.getGenre());
        }
        if (movieDetailsToUpdate.getCountry() == null) {
            movieDetailsToUpdate.setCountry(movieDetails.getCountry());
        }
        if (movieDetailsToUpdate.getRating() == 0) {
            movieDetailsToUpdate.setRating(movieDetails.getRating());
        }
        mapToEntity(movieDetailsToUpdate, movieDetails);
        return movieDetailsRepository.save(movieDetails);
    }

    public void deleteMovieDetails(UUID id) {
        final MovieDetails details = movieDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MovieDetails with id " + id + " not found"));
        movieRepository.findByMovieDetails_Id(details.getId())
                .ifPresent(movie -> movie.setMovieDetails(null));
        movieDetailsRepository.deleteById(id);
    }

    public void deleteAllMovieDetails() {
        movieDetailsRepository.deleteAll();
    }

    private MovieDetailsDTO mapToDTO(final MovieDetails movieDetails,
                                     final MovieDetailsDTO movieDetailsDTO) {
        movieDetailsDTO.setId(movieDetails.getId());
        movieDetailsDTO.setDescription(movieDetails.getDescription());
        movieDetailsDTO.setPoster(movieDetails.getPoster());
        movieDetailsDTO.setDuration(movieDetails.getDuration());
        movieDetailsDTO.setReleaseYear(movieDetails.getReleaseYear());
        movieDetailsDTO.setGenre(movieDetails.getGenre());
        movieDetailsDTO.setCountry(movieDetails.getCountry());
        movieDetailsDTO.setRating(movieDetails.getRating());
        return movieDetailsDTO;
    }

    private void mapToEntity(final MovieDetailsDTO movieDetailsDTO,
                             final MovieDetails movieDetails) {
        movieDetails.setDescription(movieDetailsDTO.getDescription());
        movieDetails.setPoster(movieDetailsDTO.getPoster());
        movieDetails.setDuration(movieDetailsDTO.getDuration());
        movieDetails.setReleaseYear(movieDetailsDTO.getReleaseYear());
        movieDetails.setGenre(movieDetailsDTO.getGenre());
        movieDetails.setCountry(movieDetailsDTO.getCountry());
        movieDetails.setRating(movieDetailsDTO.getRating());
    }
}
