package una.ac.cr.backend.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PersonaAOP extends BaseAOP{
    String entidad = "Persona";
    @Override
    @Before("execution(* una.ac.cr.backend.repositories.PersonaRepository.findById(..))")
    public void logFindById(JoinPoint joinPoint) {
        super.logFindById(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.PersonaRepository.findAll(..))")
    public void logFindAll(JoinPoint joinPoint) {
        super.logFindAll(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.PersonaRepository.save(..))")
    public void logSave(JoinPoint joinPoint) {
        super.logSave(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.PersonaRepository.delete(..))")
    public void logDelete(JoinPoint joinPoint) {
        super.logDelete(joinPoint);
    }

    @Override
    public String getEntidad() {
        return entidad;
    }
}
