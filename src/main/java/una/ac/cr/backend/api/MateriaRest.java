package una.ac.cr.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.backend.entities.Materia;
import una.ac.cr.backend.repositories.MateriaRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materia")
public class MateriaRest {
    @Autowired
    private MateriaRepository materiaRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Materia>> findAll() {
        List<Materia> materias = new ArrayList<>();
        materiaRepository.findAll().forEach(materias::add);
        return ResponseEntity.ok(materias);
    }


    @GetMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Materia> findById(@PathVariable BigInteger id) {
        Optional<Materia> materia = materiaRepository.findById(id);
        if (!materia.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(materia.get());
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Materia> create(@RequestBody Materia materia) {
        return ResponseEntity.ok(materiaRepository.save(materia));
    }

    @PutMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Materia> update(@RequestBody Materia materia){
        if (!materiaRepository.findById(materia.getId()).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(materiaRepository.save(materia));
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable BigInteger id) {
        if (!materiaRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        materiaRepository.delete(materiaRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }
}
