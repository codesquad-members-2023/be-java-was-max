package webserver.http.request.component;

import webserver.http.parser.HttpRequestParser;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestMessageBody {

    private Map<String, String> parameter;

    public RequestMessageBody(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public static RequestMessageBody parseMessageBody(String messageBodyString) {
        if (messageBodyString.isEmpty()) {
            return new RequestMessageBody(new HashMap<>());
        }

        Map<String, String> messageBodyMap = new HashMap<>();
        String[] params = messageBodyString.split(HttpRequestParser.QUERYSTRING_SEPARATOR);
        for (String param : params) {
            String[] tokens = param.split(HttpRequestParser.KEY_VALUE_SEPARATOR);
            String key = URLDecoder.decode(tokens[HttpRequestParser.KEY_INDEX], UTF_8);
            String value = "";
            if (tokens.length == 2) {
                value = URLDecoder.decode(tokens[HttpRequestParser.VALUE_INDEX], UTF_8);
            }
            messageBodyMap.put(key, value);
        }
        return new RequestMessageBody(messageBodyMap);
    }

    public String get(Object key) {
        return parameter.get(key);
    }

    public String put(String key, String value) {
        return parameter.put(key, value);
    }

    @Override
    public String toString() {
        return parameter.keySet()
                .stream()
                .map(key -> String.format("%s=%s", key, parameter.get(key)))
                .collect(Collectors.joining("&"));
    }
}
