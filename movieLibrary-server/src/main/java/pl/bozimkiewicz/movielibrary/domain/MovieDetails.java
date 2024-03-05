package pl.bozimkiewicz.movielibrary.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Year;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class MovieDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, unique = true)
    private UUID id;
    private String description;
    private String poster;
    private Duration duration;
    private Year releaseYear;
    private String genre;
    private String country;
    private double rating;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "movieDetails")
    private Movie movie;

    public MovieDetails(
            String description,
            String poster,
            Duration duration,
            Year releaseYear,
            String genre,
            String country,
            double rating
    ) {
        this.description = description;
        this.poster = poster;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.country = country;
        this.rating = rating;
    }
}
