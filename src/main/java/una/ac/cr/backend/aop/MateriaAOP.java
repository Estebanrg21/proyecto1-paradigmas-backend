package una.ac.cr.backend.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MateriaAOP extends BaseAOP{

    String entidad = "Materia";

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.MateriaRepository.findById(..))")
    public void logFindById(JoinPoint joinPoint) {
        super.logFindById(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.MateriaRepository.findAll(..))")
    public void logFindAll(JoinPoint joinPoint) {
        super.logFindAll(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.MateriaRepository.save(..))")
    public void logSave(JoinPoint joinPoint) {
        super.logSave(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.MateriaRepository.delete(..))")
    public void logDelete(JoinPoint joinPoint) {
        super.logDelete(joinPoint);
    }

    @Override
    public String getEntidad() {
        return entidad;
    }
}
