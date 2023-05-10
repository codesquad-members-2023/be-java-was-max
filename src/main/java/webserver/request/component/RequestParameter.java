package webserver.request.component;

import java.util.Map;

public class RequestParameter {

    private Map<String, String> parameter;

    public RequestParameter(Map<String, String> parameter) {
        this.parameter = parameter;
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
            sb.append(key).append("=").append(parameter.get(key)).append(" ");
        }
        return String.join("&", sb.toString().trim().split(" "));
    }
}
