package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.util.HttpResponseUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpResponseTest {

    @Test
    @DisplayName("HttpResponse 객체는 statusCode, header 를 바탕으로 응답헤더를 bytes로 만들어 반환한다.")
    void addHeader() {
        HttpResponseUtils httpResponse = new HttpResponseUtils();

        httpResponse.setStatusCode(200);
        httpResponse.addHeader("Name", "Sully");

        String sb = "HTTP/1.1 200 OK \r\n" +
                "Name: Sully\r\n" +
                "\r\n";

        byte[] expected = sb.getBytes();

        assertEquals(Arrays.toString(expected), Arrays.toString(httpResponse.toBytes()));
    }
}