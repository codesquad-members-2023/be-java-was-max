package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public HttpRequest() {
    }

    public static String separateUrl(String line) {
        String url = extractUrl(line);

        if (url.contains("?")) {
            url = url.split("\\?")[0];
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return url;
    }

    public static String separateMethod(String line) {
        return line.split(" ")[0];
    }

    public static Map<String, String> separateParam(String line) {
        String url = extractUrl(line);

        if (!url.contains("?")) {
            return null;
        }

        String[] params = url.split("\\?")[1].split("&");

        Map<String, String> paramMap = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            paramMap.put(keyValue[0], keyValue[1]);
        }

        return paramMap;
    }

    private static String extractUrl(String line) {
        return line.split(" ")[1];
    }
}
