package una.ac.cr.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.backend.entities.Periodo;
import una.ac.cr.backend.repositories.PeriodoRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/periodo")
public class PeriodoRest {
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
    public ResponseEntity<Periodo> findById(@PathVariable BigInteger id) {
        Optional<Periodo> periodo = periodoRepository.findById(id);
        if (!periodo.isPresent()) {
            ResponseEntity.badRequest().build();
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
    public ResponseEntity<Periodo> update(@RequestBody Periodo periodo){
        if (!periodoRepository.findById(periodo.getId()).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(periodoRepository.save(periodo));
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable BigInteger id) {
        if (!periodoRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        periodoRepository.delete(periodoRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }


}
