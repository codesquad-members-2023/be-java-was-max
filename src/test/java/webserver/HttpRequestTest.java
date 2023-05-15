package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.request.HttpRequest;

class HttpRequestTest {

	@Test
	@DisplayName("Request의 startLine에 대한 정보는 HttpRequest객체에 저장된다.")
	void httpRequestTest() {
		//given
		String URL = "GET /index.html?name=charlie HTTP/1.1" + "\n"
			+ "Content-Length: 0" + "\n";
		Map<String, String> expectedMap = Map.of("name", "charlie");
		InputStream in = new ByteArrayInputStream(URL.getBytes(StandardCharsets.UTF_8));

		//when
		HttpRequest httpRequest = new HttpRequest(in);

		//then
		assertThat(httpRequest.getMethod()).isEqualTo("GET");
		assertThat(httpRequest.getURL()).isEqualTo("/index.html?name=charlie");
		assertThat(httpRequest.getQueryParams()).isEqualTo(expectedMap);
	}
}