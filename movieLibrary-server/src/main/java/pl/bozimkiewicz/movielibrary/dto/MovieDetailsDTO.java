package pl.bozimkiewicz.movielibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Year;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsDTO {
    private UUID id;
    private String description;
    private String poster;
    private Duration duration;
    private Year releaseYear;
    private String genre;
    private String country;
    private double rating;
}
