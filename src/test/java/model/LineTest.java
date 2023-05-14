package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LineTest {
    @Test
    @DisplayName("css 경로 반환 확인")
    void testRequestCss() {
        String url = "/css/bootstrap.mis.css";
        String expectedUrl = "src/main/resources/static" + url;
        Line line = new Line("", url, Map.of());

        assertThat(expectedUrl).isEqualTo(line.separateAbsolutePath());
        assertThat("css").isEqualTo(line.separateRequestType());
    }

    @Test
    @DisplayName("html 경로 반환 확인")
    void testRequestHtml() {
        String url = "/";
        String expectedUrl = "src/main/resources/templates" + "/index.html";
        Line line = new Line("", url, Map.of());

        assertThat(expectedUrl).isEqualTo(line.separateAbsolutePath());
        assertThat("html").isEqualTo(line.separateRequestType());
    }
}
