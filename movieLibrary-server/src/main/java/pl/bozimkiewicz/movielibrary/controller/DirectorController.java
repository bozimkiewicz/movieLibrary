package pl.bozimkiewicz.movielibrary.controller;

import org.springframework.web.bind.annotation.*;
import pl.bozimkiewicz.movielibrary.domain.Director;
import pl.bozimkiewicz.movielibrary.dto.DirectorDTO;
import pl.bozimkiewicz.movielibrary.service.DirectorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("")
    public List<DirectorDTO> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    public DirectorDTO getDirectorById(@PathVariable UUID id) {
        return directorService.getDirectorById(id);
    }

    @PostMapping("")
    public Director addDirector(@RequestBody DirectorDTO directorDTO) {
        return directorService.addDirector(directorDTO);
    }

    @PutMapping("/{id}")
    public Director updateDirector(@PathVariable UUID id, @RequestBody DirectorDTO directorToUpdate) {
        return directorService.updateDirector(id, directorToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteDirector(@PathVariable UUID id) {
        directorService.deleteDirector(id);
    }

    @DeleteMapping("/all")
    public void deleteAllDirectors() {
        directorService.deleteAllDirectors();
    }
}
