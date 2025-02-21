package utility;

import com.google.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transaction;

import java.time.LocalDateTime;
import java.util.*;

public class JavaUtility {

    private final EntityManager entityManager;

    @Inject
    public JavaUtility(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static Map<String, String> getResponse(String message, String status){
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", message);
        response.put("status", status);
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }

}
