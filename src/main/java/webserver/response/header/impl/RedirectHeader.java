package webserver.response.header.impl;

import session.Session;
import webserver.response.HttpResponseParams;
import webserver.response.header.HttpResponseHeader;

public class RedirectHeader implements HttpResponseHeader {

	private String redirectLocation;
	private String header;
	private final Session session;

	public RedirectHeader(HttpResponseParams httpResponseParams) {
		this.redirectLocation = extractSubstringAfterColon(httpResponseParams.getViewName());
		this.session = httpResponseParams.getSession();
		createHeader();
	}

	/**
	 * redirect 할 location을 추출한다.
	 * @param view
	 * @return
	 */
	private String extractSubstringAfterColon(String view) {
		return view.substring(view.indexOf(":") + 1);
	}

	private void createHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("Location: ").append(redirectLocation).append("\r\n");

		if (session.getUUID() != null) {
			sb.append(String.format("Set-Cookie: sid=%s; Path=/\r\n", session.getUUID()));
		}

		header = sb.toString();
	}

	@Override
	public String toString() {
		return header;
	}
}
