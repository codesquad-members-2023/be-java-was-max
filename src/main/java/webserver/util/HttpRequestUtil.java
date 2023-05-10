package webserver.util;

import java.util.HashMap;
import java.util.Map;

public final class HttpRequestUtil {

    private HttpRequestUtil() {

    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> paramMap = new HashMap<>();
        String[] params = queryString.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            paramMap.put(split[0], split[1]);
        }
        return paramMap;
    }
}
