package una.ac.cr.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.backend.businessExceptions.BusinessSQLException;
import una.ac.cr.backend.entities.Matricula;
import una.ac.cr.backend.repositories.MatriculaRepository;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/matricula")
public class MatriculaRest {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Matricula>> findAll() {
        List<Matricula> matriculas = new ArrayList<>();
        matriculaRepository.findAll().forEach(matriculas::add);
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Matricula> findById(@PathVariable BigInteger id) {
        Optional<Matricula> matricula = matriculaRepository.findById(id);
        if (!matricula.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(matricula.get());
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Matricula> create(@RequestBody Matricula matricula) {
        return ResponseEntity.ok(matriculaRepository.save(matricula));
    }

    @PutMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Matricula> update(@RequestBody Matricula matricula){
        if (!matriculaRepository.findById(matricula.getId()).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(matriculaRepository.save(matricula));
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable BigInteger id) {
        if (!matriculaRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        matriculaRepository.delete(matriculaRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<Object> handleException(JpaSystemException e) {
        if (BusinessSQLException.isBusinessSQLError(e)){
            String json = "{"+
                    "\"error\":"+"\""+BusinessSQLException.getCauseMessage(e)+"\"" + "," +
                    "\"status\""+HttpStatus.BAD_REQUEST.value() +
                    "}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json);
        }
        throw e;
    }
}
