package una.ac.cr.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import una.ac.cr.backend.entities.Log;

import java.math.BigInteger;


public interface LogRepository extends CrudRepository<Log, BigInteger> {
}
