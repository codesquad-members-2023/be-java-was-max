package webserver.response;

import webserver.response.header.HttpResponseHeader;
import webserver.response.header.impl.OkHeader;
import webserver.response.header.impl.RedirectHeader;
import webserver.response.statusline.HttpResponseStatusLine;
import webserver.response.statusline.impl.OkStatusLine;
import webserver.response.statusline.impl.RedirectStatusLine;

public class HttpResponse {

	private final HttpResponseStatusLine httpResponseStatusLine;
	private final HttpResponseHeader httpResponseHeader;
	private final HttpResponseBody httpResponseBody;
	private static final String REDIRECT = "redirect";

	public HttpResponse(String view, byte[] body) {
		if (view.contains(REDIRECT)) {
			httpResponseStatusLine = new RedirectStatusLine();
			httpResponseHeader = new RedirectHeader(view);
		} else {
			httpResponseStatusLine = new OkStatusLine();
			httpResponseHeader = new OkHeader(view, body.length);
		}
		httpResponseBody = new HttpResponseBody(body);
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
