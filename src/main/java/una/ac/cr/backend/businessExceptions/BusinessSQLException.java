package una.ac.cr.backend.businessExceptions;

import org.hibernate.exception.GenericJDBCException;

import java.sql.SQLException;
import java.util.HashMap;

public class BusinessSQLException {
    private static final HashMap<String, String> sqlErrors = new HashMap<>();

    static {
        sqlErrors.put("45000", "Límite de cupos alcanzado");
        sqlErrors.put("45001","La materia con la que se desea actualizar" +
                " la matricula no cuenta con cupos disponibles");
    }

    public static boolean isBusinessSQLError(Exception e) {
        SQLException error = extractSQLException(e);
        if (error != null) {
            return sqlErrors.containsKey(error.getSQLState());
        }
        return false;
    }

    public static String getCauseMessage(Exception e) {
        SQLException error = extractSQLException(e);
        return sqlErrors.get(error.getSQLState());
    }

    private static SQLException extractSQLException(Exception e) {
        if (e.getCause() instanceof GenericJDBCException) {
            return ((GenericJDBCException) e.getCause()).getSQLException();
        }
        return null;
    }
}
