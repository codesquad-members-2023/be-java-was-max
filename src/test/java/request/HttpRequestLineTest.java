package request;

import http.HttpMethod;
import http.request.HttpRequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestLineTest {

    @Test
    @DisplayName("요청 시작 줄을 Method, Url, QueryString, Protocol로 분리한다")
    void init() {
        String startLine = "GET /user/create?userId=yeon&password=1234&name=yeon&email=yeonise%40code.com HTTP/1.1";
        HttpRequestLine httpRequestLine = new HttpRequestLine(startLine);

        assertAll(() -> assertThat(httpRequestLine.getMethod()).isEqualTo(HttpMethod.GET),
                  () -> assertThat(httpRequestLine.getUrl()).isEqualTo("/user/create"),
                  () -> assertThat(httpRequestLine.getQueryString().findValueByKey("userId")).isEqualTo("yeon"),
                  () -> assertThat(httpRequestLine.getQueryString().findValueByKey("password")).isEqualTo("1234"),
                  () -> assertThat(httpRequestLine.getQueryString().findValueByKey("name")).isEqualTo("yeon"),
                  () -> assertThat(httpRequestLine.getQueryString().findValueByKey("email")).isEqualTo("yeonise%40code.com"),
                  () -> assertThat(httpRequestLine.getProtocol()).isEqualTo("HTTP/1.1"));
    }

}
