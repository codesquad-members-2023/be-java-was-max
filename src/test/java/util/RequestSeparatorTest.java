package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestSeparator;
import webserver.util.HttpRequestUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestSeparatorTest {

    @Test
    @DisplayName("Request 의 start-line 메서드가 GET인 경우, Request 의 request-line 과 header 정보들을 파싱한다.")
    void separateGet() throws IOException {
        String request = "GET / HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "sec-ch-ua: \"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"\n" +
                "sec-ch-ua-mobile: ?0\n" +
                "sec-ch-ua-platform: \"Windows\"\n" +
                "\n";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequestUtils httpRequest = RequestSeparator.askHttpRequest(br);

        assertEquals("GET", httpRequest.getMethod());
        assertEquals(Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive",
                "sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"",
                "sec-ch-ua-mobile", "?0",
                "sec-ch-ua-platform", "\"Windows\""), httpRequest.getHeaders());
        assertNull(httpRequest.getMessageBody());
    }

    @Test
    @DisplayName("Request 의 start-line 메서드가 POST인 경우, Request 의 request-line 과 header 정보, message body를 파싱한다.")
    void separatePost() throws IOException {
        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 51\n" +
                "Cache-Control: max-age=0\n" +
                "sec-ch-ua: \"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"\n" +
                "sec-ch-ua-mobile: ?0\n" +
                "\n" +
                "userId=sully1234&password=1234&name=sully&email=123%40123";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequestUtils httpRequest = RequestSeparator.askHttpRequest(br);


        assertEquals("POST", httpRequest.getMethod());
        assertEquals(Map.of("Host", "localhost:8080"
                , "Connection", "keep-alive",
                "Content-Length", "51",
                "Cache-Control", "max-age=0",
                "sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"",
                "sec-ch-ua-mobile", "?0"), httpRequest.getHeaders());
        assertEquals("userId=sully1234&password=1234&name=sully&email=123%40123", httpRequest.getMessageBody());
    }
}
