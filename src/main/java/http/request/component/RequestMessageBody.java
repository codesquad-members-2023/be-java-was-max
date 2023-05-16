package http.request.component;

import java.util.Map;
import java.util.stream.Collectors;

public class RequestMessageBody {

    private Map<String, String> parameter;

    public RequestMessageBody(Map<String, String> parameter) {
        this.parameter = parameter;
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
