package webserver.response;

import webserver.response.header.HttpResponseHeader;
import webserver.response.header.impl.OkHeader;
import webserver.response.header.impl.RedirectHeader;
import webserver.response.statusline.HttpResponseStatusLine;
import webserver.response.statusline.impl.OkStatusLine;
import webserver.response.statusline.impl.RedirectStatusLine;

public class HttpResponseFactory {

	private static final String REDIRECT = "redirect";
	private final String viewName;

	public HttpResponseFactory(String viewName) {
		this.viewName = viewName;
	}

	public HttpResponseStatusLine httpResponseStatusLine() {
		if (isRedirect()) {
			return new RedirectStatusLine();
		}
		return new OkStatusLine();
	}

	public HttpResponseHeader httpResponseHeader(HttpResponseParams param) {
		if (isRedirect()) {
			return new RedirectHeader(param);
		}
		return new OkHeader(param);
	}

	private boolean isRedirect() {
		return viewName.startsWith(REDIRECT);
	}

}
