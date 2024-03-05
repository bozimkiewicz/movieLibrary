package pl.bozimkiewicz.movielibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorDTO {
    private UUID id;
    private String firstName;
    private String lastName;
}
