package servlet.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StartLineTest {

    @DisplayName("공백 기준으로 startLine을 구분하고 파싱한다")
    @Test
    void parse() {
        StartLine startLine = StartLine.parse("GET /index.html?name=albert;password=password HTTP/1.0");

        SoftAssertions.assertSoftly(
                softAssertions -> {
                    softAssertions.assertThat(startLine.getUrl()).isEqualTo("/index.html");
                    softAssertions.assertThat(startLine.getHttpRequestMethod()).isEqualTo(HttpRequestMethod.GET);
                    softAssertions.assertThat(startLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_0);
                }
        );
    }

    @DisplayName("path 정보가 같으면 true 아니면 false")
    @Test
    void isSamePath() {
        StartLine startLine = StartLine.parse("GET /index.html?name=albert;password=password HTTP/1.0");

        assertThat(startLine.isSamePath("/index.html")).isTrue();
        assertThat(startLine.isSamePath("/nomatch.html")).isFalse();
    }

    @DisplayName("param 개수 같으면 true 아니면 false")
    @Test
    void isSameParameterCount() {
        StartLine startLine = StartLine.parse("GET /index.html?name=albert;password=password HTTP/1.0");

        SoftAssertions.assertSoftly(
                softAssertions -> {
                    softAssertions.assertThat(startLine.isSameParameterCount(2)).isTrue();
                    softAssertions.assertThat(startLine.isSameParameterCount(1)).isFalse();
                    softAssertions.assertThat(startLine.isSameParameterCount(3)).isFalse();
                }
        );
    }

    @DisplayName("param 있으면 true 아니면 false")
    @Test
    void hasParameter() {
        StartLine startLine = StartLine.parse("GET /index.html?name=albert;password=password HTTP/1.0");

        assertThat(startLine.hasParameter()).isTrue();

        startLine = StartLine.parse("GET /index.html HTTP/1.0");

        assertThat(startLine.hasParameter()).isFalse();
    }
}
