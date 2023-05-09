package webserver;

import java.io.IOException;
import java.io.InputStream;
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
        String actual = RequestHandlerUtil.readFile(requestLine);
        //then
        Assertions.assertThat(actual).isEqualTo("Hello World");
    }
}
