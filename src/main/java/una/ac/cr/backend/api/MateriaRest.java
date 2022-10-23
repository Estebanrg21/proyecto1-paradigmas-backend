package una.ac.cr.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.backend.entities.Materia;
import una.ac.cr.backend.repositories.MateriaRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static una.ac.cr.backend.Util.jsonErrorResponse;
@RestController
@RequestMapping("/materia")
public class MateriaRest {
    private static final ResponseEntity<Object> commonResponseOnNotFound =
            ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(jsonErrorResponse(
                    "No existe la materia indicada",
                    HttpStatus.BAD_REQUEST.value()
            ));
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
    public ResponseEntity<Object> findById(@PathVariable BigInteger id) {
        Optional<Materia> materia = materiaRepository.findById(id);
        if (!materia.isPresent()) {
            return commonResponseOnNotFound;
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
    public ResponseEntity<Object> update(@RequestBody Materia materia){
        Optional<Materia> materiaOptional = materiaRepository.findById(materia.getId());
        if (!materiaOptional.isPresent()) {
            return commonResponseOnNotFound;
        }
        Materia oldMateria = materiaOptional.get();
        if (oldMateria.getMatriculas().size() > materia.getCupos()) {
            int status = HttpStatus.BAD_REQUEST.value();
            return ResponseEntity.status(status)
                    .contentType(MediaType.APPLICATION_JSON).body(jsonErrorResponse(
                    "No se puede asignar una cantidad de cupos menor a la anterior",
                    status
            ));
        } else {
            materia.setCupos(Math.abs(oldMateria.getMatriculas().size() - materia.getCupos()));
        }
        return ResponseEntity.ok(materiaRepository.save(materia));

    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable BigInteger id) {
        if (!materiaRepository.findById(id).isPresent()) {
            return commonResponseOnNotFound;
        }
        materiaRepository.delete(materiaRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }
}
