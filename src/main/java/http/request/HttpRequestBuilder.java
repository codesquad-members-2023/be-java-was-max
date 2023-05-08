package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;

import http.HttpHeaders;
import http.HttpUtils;

public final class HttpRequestBuilder {

	private static final String DELIMITER = " ";

	private HttpRequestBuilder() {
	}

	public static HttpRequest build(final BufferedReader br) throws IOException {
		RequestLine requestLine = parseStartLine(br);
		HttpHeaders httpHeaders = parseHeaders(br);

		return new HttpRequest(requestLine, httpHeaders);
	}

	private static RequestLine parseStartLine(final BufferedReader br) throws IOException {
		String startLine = br.readLine();
		String[] tokens = startLine.split(DELIMITER);

		return new RequestLine(tokens[0], URI.create(tokens[1]), tokens[2]);
	}

	private static HttpHeaders parseHeaders(final BufferedReader br) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		String headerLine;

		while (!(headerLine = br.readLine()).isEmpty()) {
			headers.addHeader(HttpUtils.parseHeader(headerLine));
		}

		return headers;
	}
}
