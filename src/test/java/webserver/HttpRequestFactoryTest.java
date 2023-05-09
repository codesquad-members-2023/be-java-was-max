package webserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestFactory;
import webserver.request.RequestLine;
import webserver.request.RequestParameter;

class HttpRequestFactoryTest {

    @Test
    @DisplayName("Inputstream이 주어지고 HttpRequestFactory에 전달하여 HttpRequest 객체를 생성한다")
    public void createHttpRequest() throws IOException {
        //given
        String requestLine = "GET /sample.html HTTP/1.0";
        String host = "Host: localhost";
        String eof = "\r\n";
        String requestHeader = String.join("\r\n", requestLine, host, eof);
        InputStream in = new ByteArrayInputStream(requestHeader.getBytes());
        //when
        HttpRequest httpRequest =
            HttpRequestFactory.createHttpRequest(new BufferedReader(new InputStreamReader(in)));
        //then

        Assertions.assertThat(httpRequest.getRequestLine().getMethod()).isEqualTo("GET");
        Assertions.assertThat(httpRequest.getRequestLine().getRequestURI())
            .isEqualTo("/sample.html");
        Assertions.assertThat(httpRequest.getRequestLine().getHttpVersion()).isEqualTo("HTTP/1.0");
        Assertions.assertThat(httpRequest.getHeader().get("Host")).isEqualTo("localhost");
    }
}
