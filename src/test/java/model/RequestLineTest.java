package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequestLineTest {
    @Test
    @DisplayName("css 경로 반환 확인")
    void testRequestCss() {
        String url = "/css/bootstrap.mis.css";
        String expectedUrl = "src/main/resources/static" + url;
        RequestLine requestLine = new RequestLine("", url, Map.of());

        assertThat(expectedUrl).isEqualTo(requestLine.separateAbsolutePath());
        assertThat("css").isEqualTo(requestLine.separateRequestType());
    }

    @Test
    @DisplayName("html 경로 반환 확인")
    void testRequestHtml() {
        String url = "/";
        String expectedUrl = "src/main/resources/templates" + "/index.html";
        RequestLine requestLine = new RequestLine("", url, Map.of());

        assertThat(expectedUrl).isEqualTo(requestLine.separateAbsolutePath());
        assertThat("html").isEqualTo(requestLine.separateRequestType());
    }
}
