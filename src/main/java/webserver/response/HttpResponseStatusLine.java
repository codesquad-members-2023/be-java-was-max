package webserver.response;

public class HttpResponseStatusLine {

	private static final String HTTP_VERSION = "HTTP/1.1";

	private String httpStatusCode;

	private String httpStatusMessage;

	public HttpResponseStatusLine(String view) {
		createHttpStatusCode(view);
		createHttpStatusMessage(view);
	}

	private void createHttpStatusCode(String view) {
		if (view.contains("redirect")) {
			httpStatusCode = "302";
		} else {
			httpStatusCode = "200";
		}
	}

	private void createHttpStatusMessage(String view) {
		if (view.contains("redirect")) {
			httpStatusMessage = "Found";
		} else {
			httpStatusMessage = "OK";
		}
	}

	@Override
	public String toString() {
		return HTTP_VERSION + " " + httpStatusCode + " " + httpStatusMessage + " " + "\r\n";
	}
}
