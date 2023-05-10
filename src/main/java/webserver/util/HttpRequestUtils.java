package webserver.util;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    private HttpRequestUtils() {
    }

    public static String parseUrl(String line) {
        String url = extractUrl(line);

        if (url.contains("?")) {
            url = url.split("\\?")[0];
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return url;
    }

    public static String parseMethod(String line) {
        return line.split(" ")[0];
    }

    public static Map<String, String> parseQueryString(String line) {
        String url = extractUrl(line);

        if (!url.contains("?")) {
            return null;
        }

        String[] params = url.split("\\?")[1].split("&");

        Map<String, String> quaryMap = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            quaryMap.put(keyValue[0], keyValue[1]);
        }

        return quaryMap;
    }

    private static String extractUrl(String line) {
        return line.split(" ")[1];
    }
}
