package http.request.component;

import static http.parser.HttpRequestParser.KEY_INDEX;
import static http.parser.HttpRequestParser.KEY_VALUE_SEPARATOR;
import static http.parser.HttpRequestParser.QUERYSTRING_SEPARATOR;
import static http.parser.HttpRequestParser.VALUE_INDEX;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        String[] params = messageBodyString.split(QUERYSTRING_SEPARATOR);
        for (String param : params) {
            String[] tokens = param.split(KEY_VALUE_SEPARATOR);
            String key = URLDecoder.decode(tokens[KEY_INDEX], UTF_8);
            String value = URLDecoder.decode(tokens[VALUE_INDEX], UTF_8);
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
