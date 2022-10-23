package una.ac.cr.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.backend.entities.Periodo;
import una.ac.cr.backend.repositories.PeriodoRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static una.ac.cr.backend.Util.jsonErrorResponse;

@RestController
@RequestMapping("/periodo")
public class PeriodoRest {
    private static final ResponseEntity<Object> commonResponseOnNotFound =
            ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(jsonErrorResponse(
                    "No existe el periodo indicado",
                    HttpStatus.BAD_REQUEST.value()
            ));
    @Autowired
    private PeriodoRepository periodoRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Periodo>> findAll() {
        List<Periodo> periodos = new ArrayList<>();
        periodoRepository.findAll().forEach(periodos::add);
        return ResponseEntity.ok(periodos);
    }


    @GetMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Object> findById(@PathVariable BigInteger id) {
        Optional<Periodo> periodo = periodoRepository.findById(id);
        if (!periodo.isPresent()) {
            return commonResponseOnNotFound;
        }
        return ResponseEntity.ok(periodo.get());
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Periodo> create(@RequestBody Periodo periodo) {
        return ResponseEntity.ok(periodoRepository.save(periodo));
    }

    @PutMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Object> update(@RequestBody Periodo periodo){
        if (!periodoRepository.findById(periodo.getId()).isPresent()) {
            return commonResponseOnNotFound;
        }
        return ResponseEntity.ok(periodoRepository.save(periodo));
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable BigInteger id) {
        if (!periodoRepository.findById(id).isPresent()) {
            return commonResponseOnNotFound;
        }
        periodoRepository.delete(periodoRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }


}
