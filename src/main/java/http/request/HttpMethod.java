package http.request;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private final String value;

    HttpMethod(final String value){
        this.value = value;
    }

    public static HttpMethod get(final String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.value.equals(method))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("지원하지 않는 메서드 입니다."));
    }
}
