package response;

import response.header.HttpResponseHeader;
import response.header.impl.OkHeader;
import response.header.impl.RedirectHeader;
import response.statusline.HttpResponseStatusLine;
import response.statusline.impl.OkStatusLine;
import response.statusline.impl.RedirectStatusLine;

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
