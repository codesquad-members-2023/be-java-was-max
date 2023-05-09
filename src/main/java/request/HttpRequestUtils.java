package request;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequestUtils {

    private HttpRequestUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> parameters = Maps.newHashMap();
        String[] tokens = queryString.split("&");

        for (String token : tokens) {
            StringTokenizer tokenizer = new StringTokenizer(token, "=");
            parameters.put(tokenizer.nextToken(), tokenizer.nextToken());
        }

        return parameters;
    }
}
