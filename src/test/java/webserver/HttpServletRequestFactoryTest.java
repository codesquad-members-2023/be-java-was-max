package webserver;

import static http.common.HttpMethod.GET;

import http.request.HttpRequestFactory;
import http.request.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpServletRequestFactoryTest {

    @Test
    @DisplayName("Inputstream이 주어지고 HttpRequestFactory에 전달하여 HttpRequest 객체를 생성한다")
    void createHttpRequest() throws IOException {
        // given
        String requestHeader = String.join("\r\n",
            "GET /sample.html HTTP/1.0",
            "Host: localhost",
            "\r\n");
        BufferedReader br = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(requestHeader.getBytes())));
        // when
        HttpServletRequest httpServletRequest = HttpRequestFactory.create(br);
        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpServletRequest.getHttpMethod()).isEqualTo(GET);
        softAssertions.assertThat(httpServletRequest.getRequestURI().getPath())
            .isEqualTo("/sample.html");
        softAssertions.assertThat(httpServletRequest.getProtocolVersion().toString())
            .isEqualTo("HTTP/1.0");
        softAssertions.assertThat(httpServletRequest.getHeader("Host"))
            .isEqualTo("localhost");
        softAssertions.assertAll();
    }
}
