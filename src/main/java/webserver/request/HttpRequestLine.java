package webserver.request;

import static utils.HttpRequestUtils.*;

import java.util.Map;

public class HttpRequestLine {

	private String method;
	private String URL;
	private Map<String, String> queryParams;
	private static final String SPACE = " ";

	public HttpRequestLine(String requestLine) {
		this.method = extractMethod(requestLine);
		this.URL = extractURL(requestLine);
		this.queryParams = parseQueryString(extractQueryString());
	}

	private String extractMethod(String startLine) {
		String[] tokens = startLine.split(SPACE);
		return tokens[0];
	}

	private String extractURL(String startLine) {
		String[] tokens = startLine.split(SPACE);
		return decode(tokens[1]);
	}

	private String extractQueryString() {
		String[] query = URL.split("\\?");
		return query.length > 1 ? query[1] : "";
	}

	public String getMethod() {
		return method;
	}

	public String getURL() {
		return URL;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}
}

