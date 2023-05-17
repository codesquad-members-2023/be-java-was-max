package servlet.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class HttpRequestTest {


    public static final String INDEX_HTML = "/index.html";
    public static final String NO_MATCH_PATH = "/nomatch.html";

    @DisplayName("path와 param 개수를 비교한다")
    @Test
    void isMatching() {
        HttpRequest httpRequest =
                HttpRequest.of(StartLine.parse("GET /index.html HTTP/1.0"), RequestHeaders.from(new HashMap<>()));

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpRequest.isMatching(0, INDEX_HTML)).isTrue();
        softAssertions.assertThat(httpRequest.isMatching(1, INDEX_HTML)).isFalse();
        softAssertions.assertThat(httpRequest.isMatching(0, NO_MATCH_PATH)).isFalse();

        httpRequest =
                HttpRequest.of(StartLine.parse("GET /index.html?name=albert;password=password HTTP/1.0"),
                        RequestHeaders.from(new HashMap<>()));

        softAssertions.assertThat(httpRequest.isMatching(2, INDEX_HTML)).isTrue();
        softAssertions.assertThat(httpRequest.isMatching(1, INDEX_HTML)).isFalse();
        softAssertions.assertThat(httpRequest.isMatching(2, NO_MATCH_PATH)).isFalse();

        softAssertions.assertAll();
    }

    @DisplayName("Request에 Pram이 존재하면 true 아니면 false")
    @Test
    void hasParameters() {
        HttpRequest httpRequest =
                HttpRequest.of(StartLine.parse("GET /index.html HTTP/1.0"), RequestHeaders.from(new HashMap<>()));

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpRequest.hasParameters()).isFalse();

        httpRequest =
                HttpRequest.of(StartLine.parse("GET /index.html?name=albert;password=password HTTP/1.0"),
                        RequestHeaders.from(new HashMap<>()));

        softAssertions.assertThat(httpRequest.hasParameters()).isTrue();
        softAssertions.assertAll();
    }
}
