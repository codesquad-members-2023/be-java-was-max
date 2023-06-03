package webserver.http.request;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.parser.HttpRequestParser;

import java.io.*;

import static webserver.http.common.HttpMethod.POST;
import static webserver.http.common.header.GeneralHeaderType.CONNECTION;

class HttpRequestTest {

    private final String testDirectory = "src/test/resources/";

    @Test
    @DisplayName("회원가입 정보가 주어지고 사용자가 회원가입 요청할 때 쿼리스트링과 메시지 바디에 데이터를 전달할 때 파싱되어 저장된다.")
    public void request_POST2() throws IOException {
        // given
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        // when
        HttpRequest request = HttpRequestParser.parseHttpRequest(new BufferedReader(new InputStreamReader(in)));
        // then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(request.getRequestLine().getHttpMethod()).isEqualTo(POST);
        assertions.assertThat(request.getRequestLine().getRequestURI().getPath()).isEqualTo("/users");
        assertions.assertThat(request.getRequestHeader().get(CONNECTION)).isEqualTo("keep-alive");
        assertions.assertThat(request.getQueryString().get("id")).isEqualTo("1");
        assertions.assertThat(request.getMessageBody().get("userId")).isEqualTo("javajigi");
    }
}
