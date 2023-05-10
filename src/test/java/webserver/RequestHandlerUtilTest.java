package webserver;

import static org.assertj.core.api.Assertions.*;
import static webserver.request.common.HttpMethod.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.component.RequestParameter;
import webserver.request.component.RequestURI;
import webserver.response.HttpVersion;
import webserver.util.RequestHandlerUtil;
import webserver.request.component.RequestLine;

class RequestHandlerUtilTest {

    @Test
    @DisplayName("urlPath가 주어지고 urlPath 위치의 파일을 읽어서 파일 내용을 응답한다")
    void readFile() throws IOException {
        // given
        RequestURI requestURI = new RequestURI("/sample.html",
            new RequestParameter(new HashMap<>()));
        RequestLine requestLine = new RequestLine(GET, requestURI, new HttpVersion(1.0));
        // when
        byte[] bytes = RequestHandlerUtil.readFile(requestLine.getRequestURI().getPath());
        // then
        BufferedReader br = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(bytes)));
        assertThat(br.readLine()).isEqualTo("Hello World");
    }
}
