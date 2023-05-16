package webserver.response.header.impl;

import webserver.response.header.HttpResponseHeader;

public class RedirectHeader implements HttpResponseHeader {

	private String redirectLocation;
	private String header;

	public RedirectHeader(String viewName) {
		this.redirectLocation = extractSubstringAfterColon(viewName);
		createRedirectHeader();
	}

	/**
	 * redirect 할 location을 추출한다.
	 * @param view
	 * @return
	 */
	private String extractSubstringAfterColon(String view) {
		return view.substring(view.indexOf(":") + 1);
	}

	private void createRedirectHeader() {
		header = "Location: " + redirectLocation + "\r\n";
	}

	@Override
	public String toString() {
		return header;
	}
}
