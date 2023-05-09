package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {
    @Test
    @DisplayName("Header 첫 번째 라인에서 요청 URL 추출 확인")
    void testSeparateUrl() {
        // 기본 URL 처리
        String url1 = HttpRequest.separateUrl("GET /index.html HTTP/1.1");
        assertEquals("/index.html", url1);

        // URL에 파라미터가 포함된 경우
        String url2 = HttpRequest.separateUrl("GET /index.html?id=sully HTTP/1.1");
        assertEquals("/index.html", url2);

        // URL에 경로가 포함된 경우
        String url3 = HttpRequest.separateUrl("GET /user/sully123 HTTP/1.1");
        assertEquals("/user/sully123", url3);

        // URL이 "/"인 경우
        String url4 = HttpRequest.separateUrl("GET / HTTP/1.1");
        assertEquals("/index.html", url4);
    }
}