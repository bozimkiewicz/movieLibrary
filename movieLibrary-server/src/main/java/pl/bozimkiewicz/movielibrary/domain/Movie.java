package pl.bozimkiewicz.movielibrary.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, unique = true)
    private UUID id;
    private String title;
    private Boolean watched;
    @OneToOne
    private MovieDetails movieDetails;
    @ManyToOne
    private Director director;
    @ManyToMany
    private List<Actor> actors;

    public Movie(String title, Boolean watched) {
        this.title = title;
        this.watched = watched;
    }
}
