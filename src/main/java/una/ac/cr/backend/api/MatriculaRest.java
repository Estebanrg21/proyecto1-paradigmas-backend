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
import una.ac.cr.backend.repositories.MatriculaRepository;
import una.ac.cr.backend.repositories.PersonaRepository;

import java.math.BigInteger;
import java.util.*;

import static una.ac.cr.backend.Util.jsonErrorResponse;

@RestController
@RequestMapping("/matricula")
public class MatriculaRest {
    private static final ResponseEntity<Object> commonResponseOnNotFound =
            ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(jsonErrorResponse(
                    "No existe la matrícula indicada",
                    HttpStatus.BAD_REQUEST.value()
            ));

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Matricula>> findAll() {
        List<Matricula> matriculas = new ArrayList<>();
        matriculaRepository.findAll().forEach(matriculas::add);
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Object> findById(@PathVariable BigInteger id) {
        Optional<Matricula> matricula = matriculaRepository.findById(id);
        if (!matricula.isPresent()) {
            return commonResponseOnNotFound;
        }
        return ResponseEntity.ok(matricula.get());
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Object> create(@RequestBody Matricula matricula) {
        return resolveMatriculaResponse(matricula, getMatriculaReady(matricula));
    }

    @PutMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Object> update(@RequestBody Matricula matricula) {
        Optional<Matricula> oldMatriculaOptional = matriculaRepository.findById(matricula.getId());
        if (!oldMatriculaOptional.isPresent()) {
            return commonResponseOnNotFound;
        }
        if (oldMatriculaOptional.get().areAttributesTheSame(matricula)) {
            return ResponseEntity.accepted().body(matricula);
        }
        Object[] matriculateReadiness = getMatriculaReady(oldMatriculaOptional.get(), matricula);
        if (matriculateReadiness[1] != null) {
            Matricula oldMatricula = oldMatriculaOptional.get();
            if (!oldMatricula.getMateria().getId().equals(((Materia) matriculateReadiness[1]).getId())) {
                try {
                    Materia oldMateria = oldMatricula.getMateria();
                    oldMateria.setCupos(oldMateria.getCupos() + 1);
                    materiaRepository.save(oldMateria);
                } catch (Exception e) {
                    matriculateReadiness[0] = "No fue posible actualizar";
                    matriculateReadiness[2] = HttpStatus.INTERNAL_SERVER_ERROR.value();
                }
            }
        }
        return resolveMatriculaResponse(matricula, matriculateReadiness);
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable BigInteger id) {
        Optional<Matricula> matriculaOptional = matriculaRepository.findById(id);
        if (!matriculaOptional.isPresent()) {
            return commonResponseOnNotFound;
        }
        return resolveMatriculaResponse(matriculaOptional.get(),
                getMatriculaReady(matriculaOptional.get(), true), true);
    }

    private ResponseEntity<Object> resolveMatriculaResponse(Matricula matricula, Object[] resultMatricula) {
        return resolveMatriculaResponse(matricula, resultMatricula, false);
    }

    private ResponseEntity<Object> resolveMatriculaResponse(Matricula matricula, Object[] resultMatricula,
                                                            boolean isDeletion) {
        Materia materia = (Materia) resultMatricula[1];
        String msg = (String) resultMatricula[0];
        Integer status = (Integer) resultMatricula[2];
        if (materia != null) {
            try {
                materiaRepository.save(materiaRepository.save(materia));
                if (isDeletion) {
                    matriculaRepository.delete(matricula);
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.ok(matriculaRepository.save(matricula));
                }
            } catch (Exception e) {
                msg = "Hubo un error en la interacción con la base de datos";
                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
        }
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON)
                .body(jsonErrorResponse(msg, status));
    }

    private Object[] getMatriculaReady(Matricula matricula) {
        return getMatriculaReady(matricula, false);
    }

    private Object[] getMatriculaReady(Matricula oldMatricula, Matricula newMatricula) {
        return (oldMatricula.getMateria().getId().equals(newMatricula.getMateria().getId())) ?
                new Object[]{"", oldMatricula.getMateria(), HttpStatus.OK.value()} :
                getMatriculaReady(newMatricula, false);
    }

    private Object[] getMatriculaReady(Matricula matricula, boolean isDeletion) {
        Optional<Materia> materiaOptional = materiaRepository.findById(matricula.getMateria().getId());
        Optional<Persona> personaOptional = personaRepository.findById(matricula.getPersona().getId());
        if (materiaOptional.isPresent() && personaOptional.isPresent()) {
            Materia materia = materiaOptional.get();
            if (!(materia.getCupos() > 0) && !isDeletion) {
                return new Object[]{"Límite de cupos alcanzado", null, HttpStatus.BAD_REQUEST.value()};
            } else {
                materia.setCupos(materia.getCupos() + ((isDeletion) ? 1 : -1));
                return new Object[]{"", materia, HttpStatus.OK.value()};
            }
        }
        return new Object[]{"Datos no válidos", null, HttpStatus.BAD_REQUEST.value()};
    }
}
