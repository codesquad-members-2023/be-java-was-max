package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    @Test
    @DisplayName("요청 시작 줄을 Method, Url, QueryString, Protocol로 분리한다")
    void init() {
        String startLine = "GET /user/create?userId=yeon&password=1234&name=yeon&email=yeonise%40code.com HTTP/1.1";
        HttpRequest httpRequest = new HttpRequest(startLine);

        assertAll(() -> assertThat(httpRequest.getMethod()).isEqualTo("GET"),
                  () -> assertThat(httpRequest.getUrl()).isEqualTo("/user/create"),
                  () -> assertThat(httpRequest.getQueryString())
                          .containsEntry("userId", "yeon")
                          .containsEntry("password", "1234")
                          .containsEntry("name", "yeon")
                          .containsEntry("email", "yeonise%40code.com"),
                  () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1"));
    }

}
