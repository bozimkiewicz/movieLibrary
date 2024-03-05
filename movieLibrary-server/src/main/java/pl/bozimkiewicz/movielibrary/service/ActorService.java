package pl.bozimkiewicz.movielibrary.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.bozimkiewicz.movielibrary.domain.Actor;
import pl.bozimkiewicz.movielibrary.dto.ActorDTO;
import pl.bozimkiewicz.movielibrary.repository.ActorRepository;
import pl.bozimkiewicz.movielibrary.repository.MovieRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ActorService {
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    public List<ActorDTO> getAllActors() {
        final List<Actor> actors = actorRepository.findAll();
        return actors.stream()
                .map(actor -> mapToDTO(actor, new ActorDTO()))
                .toList();
    }

    public ActorDTO getActorById(UUID id) {
        return actorRepository.findById(id)
                .map(actor -> mapToDTO(actor, new ActorDTO()))
                .orElseThrow(() -> new RuntimeException("Actor with id " + id + " not found"));
    }

    public Actor addActor(ActorDTO actorDTO) {
        final Actor actor = new Actor();
        mapToEntity(actorDTO, actor);
        return actorRepository.save(actor);
    }

    public Actor updateActor(UUID id, ActorDTO actorToUpdate) {
        final Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor with id " + id + " not found"));
        if (actorToUpdate.getFirstName() == null) {
            actorToUpdate.setFirstName(actor.getFirstName());
        }
        if (actorToUpdate.getLastName() == null) {
            actorToUpdate.setLastName(actor.getLastName());
        }
        mapToEntity(actorToUpdate, actor);
        return actorRepository.save(actor);
    }

    public void deleteActor(UUID id) {
        final Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor with id " + id + " not found"));
        movieRepository.findAllByActorFullName(actor.getFirstName(), actor.getLastName())
                .forEach(movie -> movie.getActors().remove(actor));
        actorRepository.delete(actor);
    }

    public void deleteAllActors() {
        actorRepository.deleteAll();
    }

    private ActorDTO mapToDTO(final Actor actor, final ActorDTO actorDTO) {
        actorDTO.setId(actor.getId());
        actorDTO.setFirstName(actor.getFirstName());
        actorDTO.setLastName(actor.getLastName());
        return actorDTO;
    }

    private void mapToEntity(final ActorDTO actorDTO, final Actor actor) {
        actor.setFirstName(actorDTO.getFirstName());
        actor.setLastName(actorDTO.getLastName());
    }
}
