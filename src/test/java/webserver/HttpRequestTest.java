package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.util.HttpRequestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HttpRequestTest {

    private static final String PATH = "src/main/resources/templates";

    @Test
    @DisplayName("HttpRequest 객체는 request-line 에 포함된 GET 메서드를 파싱해서 반환한다.")
    void parseMethod() {

        String requestLine = "GET /index.html HTTP/1.1";
        Map<String, String> headers = Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive");

        HttpRequestUtils httpRequest = new HttpRequestUtils(requestLine, headers, null);

        assertEquals("GET", httpRequest.getMethod());
    }

    @Test
    @DisplayName("HttpRequest 객체는 request-line 에 포함된 URL(논리 경로)를 반환한다.")
    void parseUrl() {
        String requestLine = "GET /index.html HTTP/1.1";
        Map<String, String> headers = Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive");

        HttpRequestUtils httpRequest = new HttpRequestUtils(requestLine, headers, null);

        assertEquals("/index.html", httpRequest.getUrl());
    }


    @Test
    @DisplayName("HttpRequest 객체는 headers 를 맵으로 반환한다.")
    void parseHeaders() {
        String requestLine = "GET /index.html HTTP/1.1";
        Map<String, String> headers = Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive");

        HttpRequestUtils httpRequest = new HttpRequestUtils(requestLine, headers, null);

        assertEquals(headers, httpRequest.getHeaders());
    }

    @Test
    @DisplayName("GET 요청의 경우, HttpRequest 객체는 request-line 에 포함된 URL에서 파라미터를 뽑아내 parameters 맵으로 반환한다.")
    void getParams() {
        String requestLine = "GET /user/create?userId=hyun&password=1234&name=%ED%99%A9%ED%98%84&email=ghkdgus29%40naver.com HTTP/1.1";
        Map<String, String> headers = Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive");

        Map<String, String> expectedParams = Map.of("userId", "hyun",
                "password", "1234",
                "name", "%ED%99%A9%ED%98%84",
                "email", "ghkdgus29%40naver.com");

        HttpRequestUtils httpRequest = new HttpRequestUtils(requestLine, headers, null);

        assertEquals(expectedParams, httpRequest.getParameters());
    }

    @Test
    @DisplayName("GET 요청의 경우, HttpRequest 객체는 request-line 에 포함된 URL에 파라미터가 없으면 parameters 로 null을 반환한다.")
    void getNoParams() {
        String requestLine = "GET /user/create HTTP/1.1";
        Map<String, String> headers = Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive");

        HttpRequestUtils httpRequest = new HttpRequestUtils(requestLine, headers, null);

        assertNull(httpRequest.getParameters());
    }

    @Test
    @DisplayName("POST 요청의 경우, HttpRequest 객체는 message body 에서 파라미터를 뽑아내 parameters 맵으로 반환한다.")
    void getParametersBody() {
        String requestLine = "POST /user/create HTTP/1.1";
        Map<String, String> headers = Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive");
        String messageBody = "userId=hyun&password=1234&name=hyun&email=123%40123";

        HttpRequestUtils httpRequest = new HttpRequestUtils(requestLine, headers, messageBody);

        Map<String, String> expectedParameters = Map.of("userId", "hyun",
                "password", "1234",
                "name", "hyun",
                "email", "123%40123");

        assertEquals(expectedParameters, httpRequest.getParameters());
    }

    @Test
    @DisplayName("POST 요청이지만 message body 가 없는 경우에는 parameters 는 null 이다.")
    void getParameterNoBody() {
        String requestLine = "POST /user/create HTTP/1.1";
        Map<String, String> headers = Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive");

        HttpRequestUtils httpRequest = new HttpRequestUtils(requestLine, headers, null);

        assertNull(httpRequest.getParameters());
    }
}
