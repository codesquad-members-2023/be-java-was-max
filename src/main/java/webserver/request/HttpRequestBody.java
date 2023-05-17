package webserver.request;

import static utils.HttpRequestUtils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpRequestBody {

	private String body;

	private Map<String, String> formData;

	public HttpRequestBody(BufferedReader br, int contentLength) throws IOException {
		parseBody(br, contentLength);
	}

	/**
	 * POST 요청시 form-date를 읽어오고 파싱하는 작업을 하는 메서드이다.
	 * @param br
	 * @param contentLength
	 * @throws IOException
	 */
	private void parseBody(BufferedReader br, int contentLength) throws IOException {
		if (contentLength > 0) {
			char[] buffer = new char[contentLength];
			br.read(buffer, 0, contentLength);
			body = String.valueOf(buffer);
			body = decode(body);
			this.formData = parseQueryString(body);
		}
	}

	public String getBody() {
		return body;
	}

	public Map<String, String> getFormData() {
		return formData;
	}
}
