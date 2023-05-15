package webserver.response;

public class HttpResponse {

	private final HttpResponseStatusLine httpResponseStatusLine;
	private final HttpResponseHeader httpResponseHeader;
	private final HttpResponseBody httpResponseBody;

	public HttpResponse(String view, byte[] body) {
		httpResponseStatusLine = new HttpResponseStatusLine(view);
		httpResponseHeader = new HttpResponseHeader(view, body.length);
		httpResponseBody = new HttpResponseBody(body);
	}

	@Override
	public String toString() {
		return httpResponseStatusLine.toString() + httpResponseHeader + httpResponseBody;
	}
}
