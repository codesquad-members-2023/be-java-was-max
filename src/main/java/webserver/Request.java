package webserver;

import java.util.HashMap;
import java.util.Map;

public class Request {
    public Map<String, String> parseQuery(String query) {
        Map<String, String> queryMap = new HashMap<>();

        if (query != null && !query.isEmpty()) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    queryMap.put(key, value);
                }
            }
        }

        return queryMap;
    }
}
