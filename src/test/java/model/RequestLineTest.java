package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.util.HttpRequestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequestLineTest {
    @Test
    @DisplayName("요청 method가 GET이다.")
    void testParseMethod() {
        String request = "GET / HTTP/1.1";
        RequestLine requestLine = new RequestLine(HttpRequestUtils.parseMethod(request), HttpRequestUtils.parseUrl(request), HttpRequestUtils.parseQueryString(request));
        assertThat(requestLine.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("요청 url이 index다.")
    void testParseUrl() {
        String request = "GET /index.html HTTP/1.1";
        RequestLine requestLine = new RequestLine(HttpRequestUtils.parseMethod(request), HttpRequestUtils.parseUrl(request), HttpRequestUtils.parseQueryString(request));
        assertThat(requestLine.getUrl()).isEqualTo("/index.html");
    }
}
