package webserver.httpRequest.exception;

public class HttpRequestParsingException extends RuntimeException {

	private static final String HTTP_REQUEST_PARSING_EXCEPTION = "Failed to parse HTTP request";

	public HttpRequestParsingException() {
		super(HTTP_REQUEST_PARSING_EXCEPTION);
	}
}
