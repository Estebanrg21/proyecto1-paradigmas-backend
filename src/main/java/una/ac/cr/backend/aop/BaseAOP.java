package una.ac.cr.backend.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import una.ac.cr.backend.entities.Log;
import una.ac.cr.backend.repositories.LogRepository;

import java.util.Date;

public abstract class BaseAOP {

    @Autowired
    LogRepository logRepository;

    public void logFindById(JoinPoint joinPoint) {
        createLogEntry(joinPoint.getSignature().getName());
    }

    public void logFindAll(JoinPoint joinPoint) {
        createLogEntry(joinPoint.getSignature().getName());
    }

    public void logSave(JoinPoint joinPoint) {
        createLogEntry(joinPoint.getSignature().getName());
    }

    public void logDelete(JoinPoint joinPoint) {
        createLogEntry(joinPoint.getSignature().getName());
    }

    private void createLogEntry(String metodo) {
        logRepository.save(new Log(metodo, new Date(), getEntidad()));
    }

    protected abstract String getEntidad();
}
