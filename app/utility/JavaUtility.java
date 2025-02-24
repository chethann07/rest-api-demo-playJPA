package utility;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.util.*;

public class JavaUtility {

    public static Map<String, Object> getResponse(String status, String errorMessage, String responseCode, JsonNode result){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", "api.read");
        response.put("ver", "1.0");
        response.put("ts", LocalDateTime.now().toString());
        Map<String, String> params = new LinkedHashMap<>();
        params.put("resmsgid", UUID.randomUUID().toString());
        params.put("status", status);
        params.put("errmsg", errorMessage);
        response.put("params", params);
        response.put("responseCode", responseCode);
        response.put("result", result);
        return response;
    }

}
