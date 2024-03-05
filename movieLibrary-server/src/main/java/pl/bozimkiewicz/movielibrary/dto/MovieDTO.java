package pl.bozimkiewicz.movielibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private UUID id;
    private String title;
    private Boolean watched;
    private UUID movieDetails;
    private UUID director;
    private List<UUID> actors;
}
