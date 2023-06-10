package response;

import response.header.HttpResponseHeader;
import response.statusline.HttpResponseStatusLine;

public class HttpResponse {

	private final HttpResponseStatusLine httpResponseStatusLine;
	private final HttpResponseHeader httpResponseHeader;
	private final HttpResponseBody httpResponseBody;
	private final HttpResponseFactory httpResponseFactory;

	public HttpResponse(HttpResponseParams httpResponseParams) {
		httpResponseFactory = new HttpResponseFactory(httpResponseParams.getViewName());
		httpResponseStatusLine = httpResponseFactory.httpResponseStatusLine();
		httpResponseHeader = httpResponseFactory.httpResponseHeader(httpResponseParams);
		httpResponseBody = new HttpResponseBody(httpResponseParams);
	}

	public String getStatusLine() {
		return httpResponseStatusLine.toString();
	}

	public String getHeader() {
		return httpResponseHeader.toString();
	}

	public byte[] getBody() {
		return httpResponseBody.getBody();
	}
}
