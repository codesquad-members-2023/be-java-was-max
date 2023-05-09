package util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class RequestParserTest {

    @Test
    void parseStartLineTest() {
        String startLine = ("GET /user/create HTTP/1.1");

        Map<String, String> fragments = RequestParser.parseStartLine(startLine);

        assertThat(fragments.get("Method")).isEqualTo("GET");
        assertThat(fragments.get("Url")).isEqualTo("/user/create");
        assertThat(fragments.get("Protocol")).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseHeaderTest() {
        String header = ("Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n");

        Map<String, String> parsedHeader = RequestParser.parseHeader(header);

        assertThat(parsedHeader.get("Host")).isEqualTo("localhost:8080");
        assertThat(parsedHeader.get("Connection")).isEqualTo("keep-alive");
        assertThat(parsedHeader.get("Accept")).isEqualTo("*/*");
    }
}