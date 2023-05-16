package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class RequestParserTest {

    @Test
    void parseStartLineTest() {
        String startLine = ("GET /user/create HTTP/1.1");
        Map<String, String> container = new HashMap<>();

//        RequestParser.parseStartLine(startLine, container);

        assertThat(container.get("Method")).isEqualTo("GET");
        assertThat(container.get("Url")).isEqualTo("/user/create");
        assertThat(container.get("Protocol")).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseHeaderTest() {
        String header = ("Host: localhost:8080");
        Map<String, String> container = new HashMap<>();

//        RequestParser.parseHeader(header, container);

        assertThat(container.get("Host")).isEqualTo("localhost:8080");
    }
}