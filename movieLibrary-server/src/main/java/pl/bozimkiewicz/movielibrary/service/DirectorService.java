package pl.bozimkiewicz.movielibrary.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.bozimkiewicz.movielibrary.domain.Director;
import pl.bozimkiewicz.movielibrary.dto.DirectorDTO;
import pl.bozimkiewicz.movielibrary.repository.DirectorRepository;
import pl.bozimkiewicz.movielibrary.repository.MovieRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;

    public DirectorService(DirectorRepository directorRepository, MovieRepository movieRepository) {
        this.directorRepository = directorRepository;
        this.movieRepository = movieRepository;
    }

    public List<DirectorDTO> getAllDirectors() {
        final List<Director> directors = directorRepository.findAll();
        return directors.stream()
                .map(director -> mapToDTO(director, new DirectorDTO()))
                .toList();
    }

    public DirectorDTO getDirectorById(UUID id) {
        return directorRepository.findById(id)
                .map(director -> mapToDTO(director, new DirectorDTO()))
                .orElseThrow(() -> new RuntimeException("Director with id " + id + " not found"));
    }

    public Director addDirector(DirectorDTO directorDTO) {
        final Director director = new Director();
        mapToEntity(directorDTO, director);
        return directorRepository.save(director);
    }

    public Director updateDirector(UUID id, DirectorDTO directorToUpdate) {
        final Director director = directorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Director with id " + id + " not found"));
        if (directorToUpdate.getFirstName() == null) {
            directorToUpdate.setFirstName(director.getFirstName());
        }
        if (directorToUpdate.getLastName() == null) {
            directorToUpdate.setLastName(director.getLastName());
        }
        mapToEntity(directorToUpdate, director);
        return directorRepository.save(director);
    }

    public void deleteDirector(UUID id) {
        final Director director = directorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Director with id " + id + " not found"));
        movieRepository.findAllByDirector_Id(director.getId())
                .forEach(movie -> movie.setDirector(null));
        directorRepository.deleteById(id);
    }

    public void deleteAllDirectors() {
        directorRepository.deleteAll();
    }

    private DirectorDTO mapToDTO(final Director director, final DirectorDTO directorDTO) {
        directorDTO.setId(director.getId());
        directorDTO.setFirstName(director.getFirstName());
        directorDTO.setLastName(director.getLastName());
        return directorDTO;
    }

    private void mapToEntity(final DirectorDTO directorDTO, final Director director) {
        director.setFirstName(directorDTO.getFirstName());
        director.setLastName(directorDTO.getLastName());
    }
}
