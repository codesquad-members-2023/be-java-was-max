package webserver.request;

import static utils.HttpRequestUtils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpRequestBody {

	private String body;

	private Map<String, String> queryParams;

	public HttpRequestBody(BufferedReader br, int contentLength) throws IOException {
		parseBody(br, contentLength);
	}

	private void parseBody(BufferedReader br, int contentLength) throws IOException {
		if (contentLength > 0) {
			char[] buffer = new char[contentLength];
			br.read(buffer, 0, contentLength);
			body = new String(buffer);
			body = decode(body);
			this.queryParams = parseQueryString(body);
		}
	}

	public String getBody() {
		return body;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}
}
