package request;

import http.HttpMethod;
import http.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    @Test
    @DisplayName("요청 시작 줄을 Method, Url, QueryString, Protocol로 분리한다")
    void init() {
        String startLine = "GET /user/create?userId=yeon&password=1234&name=yeon&email=yeonise%40code.com HTTP/1.1";
        RequestLine requestLine = new RequestLine(startLine);

        assertAll(() -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                  () -> assertThat(requestLine.getUrl()).isEqualTo("/user/create"),
                  () -> assertThat(requestLine.getQueryString().findValueByKey("userId")).isEqualTo("yeon"),
                  () -> assertThat(requestLine.getQueryString().findValueByKey("password")).isEqualTo("1234"),
                  () -> assertThat(requestLine.getQueryString().findValueByKey("name")).isEqualTo("yeon"),
                  () -> assertThat(requestLine.getQueryString().findValueByKey("email")).isEqualTo("yeonise%40code.com"),
                  () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1"));
    }

}
