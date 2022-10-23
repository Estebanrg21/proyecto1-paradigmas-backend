package una.ac.cr.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import una.ac.cr.backend.entities.Persona;

import java.math.BigInteger;
import java.util.Optional;

public interface PersonaRepository extends CrudRepository<Persona, BigInteger> {
}
