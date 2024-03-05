package pl.bozimkiewicz.movielibrary.controller;

import org.springframework.web.bind.annotation.*;
import pl.bozimkiewicz.movielibrary.domain.Actor;
import pl.bozimkiewicz.movielibrary.dto.ActorDTO;
import pl.bozimkiewicz.movielibrary.service.ActorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("")
    public List<ActorDTO> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping("/{id}")
    public ActorDTO getActorById(@PathVariable UUID id) {
        return actorService.getActorById(id);
    }

    @PostMapping("")
    public Actor addActor(@RequestBody ActorDTO actorDTO) {
        return actorService.addActor(actorDTO);
    }

    @PutMapping("/{id}")
    public Actor updateActor(@PathVariable UUID id, @RequestBody ActorDTO actorToUpdate) {
        return actorService.updateActor(id, actorToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteActor(@PathVariable UUID id) {
        actorService.deleteActor(id);
    }

    @DeleteMapping("/all")
    public void deleteAllActors() {
        actorService.deleteAllActors();
    }
}
