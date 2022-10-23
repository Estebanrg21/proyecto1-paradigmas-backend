package una.ac.cr.backend;

public class Util {
    public static String jsonErrorResponse(String errorMsg, Integer httpStatus) {
        return "{" +
                "\"error\":" + "\"" + errorMsg + "\"" + "," +
                "\"status\":" + httpStatus +
                "}";
    }
}
