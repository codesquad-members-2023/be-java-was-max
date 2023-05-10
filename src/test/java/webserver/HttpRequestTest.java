package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpRequestTest {
    @Test
    @DisplayName("Header 첫 번째 라인에서 요청 URL 추출 확인")
    void testSeparateUrl() {
        // 기본 URL 처리
        String url1 = HttpRequest.separateUrl("GET /index.html HTTP/1.1");
        assertThat(url1).isEqualTo("/index.html");

        // URL에 파라미터가 포함된 경우
        String url2 = HttpRequest.separateUrl("GET /index.html?id=sully HTTP/1.1");
        assertThat(url2).isEqualTo("/index.html");

        // URL에 경로가 포함된 경우
        String url3 = HttpRequest.separateUrl("GET /user/sully123 HTTP/1.1");
        assertThat(url3).isEqualTo("/user/sully123");

        // URL이 "/"인 경우
        String url4 = HttpRequest.separateUrl("GET / HTTP/1.1");
        assertThat(url4).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("요청 메서드 추출 확인")
    void testSeparateMethod() {
        String line = "GET /index.html HTTP/1.1";
        String method = HttpRequest.separateMethod(line);
        assertThat(method).isEqualTo("GET");
    }

    @Test
    @DisplayName("요청 URL에서 파라미터 추출 후 Map으로 반환 확인")
    void testSeparateParam() {
        String line = "GET /user/create?userId=sully&password=1234&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=sully1234%40naver.com HTTP/1.1";

        Map<String, String> userMap = Map.of("userId", "sully",
                "password", "1234",
                "name", "%EB%B0%95%EC%9E%AC%EC%84%B1",
                "email", "sully1234%40naver.com");

        Map<String, String> paramMap = HttpRequest.separateParam(line);

        assertThat(paramMap).isEqualTo(userMap);
    }
}