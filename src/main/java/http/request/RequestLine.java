package http.request;

import java.net.URI;

public class RequestLine {

	private final String method;
	private final URI uri;
	private final String httpVersion;

	public RequestLine(String method, URI uri, String httpVersion) {
		this.method = method;
		this.uri = uri;
		this.httpVersion = httpVersion;
	}

	public String getMethod() {
		return method;
	}

	public URI getUri() {
		return uri;
	}

	@Override
	public String toString() {
		return method
			+ " "
			+ uri.toString()
			+ " "
			+ httpVersion;
	}
}
