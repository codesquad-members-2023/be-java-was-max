package http.request.component;

import static http.parser.HttpRequestParser.KEY_INDEX;
import static http.parser.HttpRequestParser.KEY_VALUE_SEPARATOR;
import static http.parser.HttpRequestParser.QUERYSTRING_SEPARATOR;
import static http.parser.HttpRequestParser.VALUE_INDEX;

import java.util.HashMap;
import java.util.Map;

public class RequestQueryString {


    private static final int QUERYSTRING_INDEX = 1;
    private Map<String, String> parameter;

    public RequestQueryString(Map<String, String> parameter) {
        this.parameter = parameter;
    }


    // queryString = id=kim&pwd=123&...
    // tokens = [id=kim, pwd=123, ...]
    public static RequestQueryString parseQueryString(String queryString) {
        Map<String, String> queryMap = createQueryMap(queryString);
        return new RequestQueryString(queryMap);
    }

    public static Map<String, String> createQueryMap(String queryString) {
        if (queryString.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, String> queryMap = new HashMap<>();
        String[] params = queryString.split(QUERYSTRING_SEPARATOR);
        for (String param : params) {
            String[] tokens = param.split(KEY_VALUE_SEPARATOR);
            queryMap.put(tokens[KEY_INDEX], tokens[VALUE_INDEX]);
        }
        return queryMap;
    }


    public String get(String key) {
        return parameter.get(key);
    }

    public void add(String key, String value) {
        parameter.put(key, value);
    }

    public void addParameter(Map<String, String> parameter) {
        this.parameter.putAll(parameter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (parameter.size() > 0) {
            sb.append("?");
        }

        for (String key : parameter.keySet()) {
            sb.append(key)
                .append("=")
                .append(parameter.get(key))
                .append(" ");
        }
        return String.join("&", sb.toString()
            .trim()
            .split(" "));
    }
}
