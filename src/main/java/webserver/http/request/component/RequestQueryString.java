package webserver.http.request.component;

import webserver.http.parser.HttpRequestParser;

import java.util.HashMap;
import java.util.Map;

public class RequestQueryString {


    private static final int QUERYSTRING_INDEX = 1;
    private Map<String, String> parameter;

    public RequestQueryString(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public static RequestQueryString parseQueryString(String queryString) {
        Map<String, String> queryMap = createQueryMap(queryString);
        return new RequestQueryString(queryMap);
    }

    public static Map<String, String> createQueryMap(String queryString) {
        if (queryString.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, String> queryMap = new HashMap<>();
        String[] params = queryString.split(HttpRequestParser.QUERYSTRING_SEPARATOR);
        for (String param : params) {
            String[] tokens = param.split(HttpRequestParser.KEY_VALUE_SEPARATOR);
            queryMap.put(tokens[HttpRequestParser.KEY_INDEX], tokens[HttpRequestParser.VALUE_INDEX]);
        }
        return queryMap;
    }

    public String get(String key) {
        return parameter.get(key);
    }

    public void add(String key, String value) {
        parameter.put(key, value);
    }

    public Map<String, String> getParameter() {
        return parameter;
    }

    public String getFormattedQueryString() {
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

    @Override
    public String toString() {
        return String.format("%s", parameter);
    }
}
