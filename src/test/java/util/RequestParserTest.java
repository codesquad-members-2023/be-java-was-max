package util;

import model.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class RequestParserTest {

    @Test
    @DisplayName("그냥 돌아가는지 확인")
    void readNParseRequestTest() throws UnsupportedEncodingException {
        String input = "POST /user/create HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 59\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        byte[] bytes = input.getBytes();
        InputStream stream = new ByteArrayInputStream(bytes);
        RequestParser parser = new RequestParser();

        Request request = parser.readNParseRequest(stream);

        assertThat(request.getBody()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}