package una.ac.cr.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import una.ac.cr.backend.entities.Matricula;

import java.math.BigInteger;

public interface MatriculaRepository extends CrudRepository<Matricula, BigInteger> {
}
