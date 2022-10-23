package una.ac.cr.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.backend.entities.Materia;
import una.ac.cr.backend.entities.Matricula;
import una.ac.cr.backend.entities.Persona;
import una.ac.cr.backend.repositories.MateriaRepository;
import una.ac.cr.backend.repositories.PersonaRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static una.ac.cr.backend.Util.jsonErrorResponse;

@RestController
@RequestMapping("/persona")
public class PersonaRest {
    private static final ResponseEntity<Object> commonResponseOnNotFound =
            ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(jsonErrorResponse(
                    "No existe la persona indicada",
                    HttpStatus.BAD_REQUEST.value()
            ));
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Persona>> findAll() {
        List<Persona> personas = new ArrayList<>();
        personaRepository.findAll().forEach(personas::add);
        return ResponseEntity.ok(personas);
    }


    @GetMapping("{identificacion}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Object> findById(@PathVariable String identificacion) {
        Optional<Persona> persona = personaRepository.findByIdentificacion(identificacion);
        if (!persona.isPresent()) {
            return commonResponseOnNotFound;
        }
        return ResponseEntity.ok(persona.get());
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Persona> create(@RequestBody Persona persona) {
        return ResponseEntity.ok(personaRepository.save(persona));
    }

    @PutMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Object> update(@RequestBody Persona persona){
        if (!personaRepository.findById(persona.getId()).isPresent()) {
            return commonResponseOnNotFound;
        }
        return ResponseEntity.ok(personaRepository.save(persona));
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable BigInteger id) {
        Optional<Persona> optionalPersona = personaRepository.findById(id);
        if (!optionalPersona.isPresent()) {
            return commonResponseOnNotFound;
        }
        Persona persona = optionalPersona.get();
        for (Matricula matricula : persona.getMatriculas()) {
            Materia materia = matricula.getMateria();
            materia.setCupos(materia.getCupos() + 1);
            materiaRepository.save(materia);
        }
        personaRepository.delete(persona);
        return ResponseEntity.ok().build();
    }
}
