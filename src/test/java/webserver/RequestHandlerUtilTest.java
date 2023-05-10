package webserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.RequestHandlerUtil;
import webserver.request.RequestLine;

class RequestHandlerUtilTest {

    @Test
    @DisplayName("urlPath가 주어지고 urlPath 위치의 파일을 읽어서 파일 내용을 응답한다")
    public void readFile() throws IOException {
        //given
        String urlPath = "/sample.html";
        RequestLine requestLine = new RequestLine("GET", urlPath, null, "HTTP/1.0");
        //when
        byte[] bytes = RequestHandlerUtil.readFile(requestLine);
        //then
        BufferedReader br = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(bytes)));
        Assertions.assertThat(br.readLine()).isEqualTo("Hello World");
    }
}
