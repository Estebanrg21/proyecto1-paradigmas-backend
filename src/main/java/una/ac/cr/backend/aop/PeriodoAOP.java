package una.ac.cr.backend.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PeriodoAOP extends BaseAOP{

    String entidad = "Periodo";

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.PeriodoRepository.findById(..))")
    public void logFindById(JoinPoint joinPoint) {
        super.logFindById(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.PeriodoRepository.findAll(..))")
    public void logFindAll(JoinPoint joinPoint) {
        super.logFindAll(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.PeriodoRepository.save(..))")
    public void logSave(JoinPoint joinPoint) {
        super.logSave(joinPoint);
    }

    @Override
    @Before("execution(* una.ac.cr.backend.repositories.PeriodoRepository.delete(..))")
    public void logDelete(JoinPoint joinPoint) {
        super.logDelete(joinPoint);
    }

    @Override
    public String getEntidad() {
        return entidad;
    }
}
