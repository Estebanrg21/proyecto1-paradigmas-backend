package una.ac.cr.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import una.ac.cr.backend.entities.Materia;

import java.math.BigInteger;

public interface MateriaRepository extends CrudRepository<Materia, BigInteger> {

}
