package webserver;

import static webserver.request.common.HttpMethod.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.request.factory.HttpRequestFactory;

class HttpRequestFactoryTest {

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
        HttpRequest httpRequest = HttpRequestFactory.create(br);
        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpRequest.getHttpMethod()).isEqualTo(GET);
        softAssertions.assertThat(httpRequest.getRequestURI().getPath()).isEqualTo("/sample.html");
        softAssertions.assertThat(httpRequest.getHttpVersion().toString()).isEqualTo("HTTP/1.0");
        softAssertions.assertThat(httpRequest.getHeader("Host"))
            .isEqualTo("localhost");
        softAssertions.assertAll();
    }
}
