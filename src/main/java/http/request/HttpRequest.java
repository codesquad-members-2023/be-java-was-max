package http.request;

import http.HttpHeaders;
import http.HttpMethod;

import java.net.URI;
import java.util.Map;

public class HttpRequest {

	private final RequestLine requestLine;
	private final QueryParameter queryParameter;
	private final HttpHeaders httpHeaders;

	public HttpRequest(final RequestLine requestLine, final QueryParameter queryParameter, final HttpHeaders httpHeaders) {
		this.requestLine = requestLine;
		this.queryParameter = queryParameter;
		this.httpHeaders = httpHeaders;
	}

	public HttpMethod getHttpMethod() {
		return requestLine.getMethod();
	}

	public URI getUri() {
		return requestLine.getUri();
	}

	public Map<String, String> getQueryParameter() {
		return queryParameter.getQueryParameter();
	}

	@Override
	public String toString() {
		return "\n[HTTP Request]\n"
				+ requestLine
				+ "\n"
				+ queryParameter
				+ "\n"
				+ httpHeaders;
	}
}
