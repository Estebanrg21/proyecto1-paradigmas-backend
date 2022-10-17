package una.ac.cr.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import una.ac.cr.backend.entities.Periodo;

import java.math.BigInteger;

public interface PeriodoRepository extends CrudRepository<Periodo, BigInteger> {
}
