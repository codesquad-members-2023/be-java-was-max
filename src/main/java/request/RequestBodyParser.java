package request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestBodyParser {
    public Map<String, String> parseQuery(String query) {
        Map<String, String> queryMap = new HashMap<>();

        if (query != null && !query.isEmpty()) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    queryMap.put(key, value);
                }
            }
        }
        return queryMap;
    }
}
