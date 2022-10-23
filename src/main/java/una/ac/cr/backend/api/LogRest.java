package una.ac.cr.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.backend.entities.Log;
import una.ac.cr.backend.repositories.LogRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogRest {
    @Autowired
    private LogRepository logRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Log>> findAll() {
        List<Log> logs = new ArrayList<>();
        logRepository.findAll().forEach(logs::add);
        return ResponseEntity.ok(logs);
    }


}

