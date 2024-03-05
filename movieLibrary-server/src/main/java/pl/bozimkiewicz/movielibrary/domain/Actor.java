package pl.bozimkiewicz.movielibrary.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, unique = true)
    private UUID id;
    private String firstName;
    private String lastName;
    @ManyToMany(mappedBy = "actors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movie> movies;

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
