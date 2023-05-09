package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpRequestUtilsTest {

    @Test
    @DisplayName("QueryString을 Key와 Value로 분리하여 저장한다")
    void parseQueryString() {
        String queryString = "apple=red&banana=yellow&sky=blue&cloud=white";
        Map<String, String> queryStrings = HttpRequestUtils.parseQueryString(queryString);

        assertAll(() -> assertThat(queryStrings.size()).isEqualTo(4),
                  () -> assertThat(queryStrings).containsEntry("apple", "red")
                                                .containsEntry("banana", "yellow")
                                                .containsEntry("sky", "blue")
                                                .containsEntry("cloud", "white"));
    }

}
