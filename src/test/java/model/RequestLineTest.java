package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestLineTest {

    @Test
    @DisplayName("RequestLine 클래스는 request-line 이 생성자에 주입되면, method 를 파싱하여 반환한다.")
    void parseMethod() {
        String requestLineString = "GET / HTTP/1.1";

        RequestLine requestLine = new RequestLine(requestLineString);

        assertEquals("GET", requestLine.getMethod());
    }

    @Test
    @DisplayName("RequestLine 클래스는 request-line 이 생성자에 주입되면, url을 파싱하여 반환한다.")
    void parseUrl() {
        String requestLineString = "GET /css/bootstrap.min.css HTTP/1.1";

        RequestLine requestLine = new RequestLine(requestLineString);

        assertEquals("/css/bootstrap.min.css", requestLine.getUrl());
    }

    @Test
    @DisplayName("RequestLine 클래스는 request-line 이 생성자에 주입되면, 적절한 RequestType 을 계산하여 반환한다.")
    void calculateRequestType() {
        String requestCSSString = "GET /css/bootstrap.min.css HTTP/1.1";
        RequestLine requestCSS = new RequestLine(requestCSSString);
        assertEquals(ContentType.CSS, requestCSS.getContentType());

        String requestHTMLString = "GET / HTTP/1.1";
        RequestLine requestHTML = new RequestLine(requestHTMLString);
        assertEquals(ContentType.HTML, requestHTML.getContentType());

        String requestJSString = "GET /js/bootstrap.min.js HTTP/1.1";
        RequestLine requestJS = new RequestLine(requestJSString);
        assertEquals(ContentType.JS, requestJS.getContentType());
    }

    @Test
    @DisplayName("RequestLine 클래스는 GET 요청이고, 쿼리 파라미터가 있는 경우, 파라미터 맵을 만들어 반환한다.)")
    void parseParamMap() {
        String requestLineString = "GET /user/create?userId=sully&password=1234&name=%ED%99%A9%ED%98%84&email=ghkdgus29%40naver.com HTTP/1.1";

        RequestLine requestLine = new RequestLine(requestLineString);

        assertEquals(Map.of("userId", "sully",
                "password", "1234",
                "name", "%ED%99%A9%ED%98%84",
                "email", "ghkdgus29%40naver.com"), requestLine.getQueryMap());
    }
}
