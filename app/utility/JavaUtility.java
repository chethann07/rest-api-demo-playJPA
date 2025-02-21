package utility;

import java.time.LocalDateTime;
import java.util.*;

public class JavaUtility {

    public static Map<String, String> getResponse(String message, String status){
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", message);
        response.put("status", status);
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
}
