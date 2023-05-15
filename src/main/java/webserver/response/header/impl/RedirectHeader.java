package webserver.response.header.impl;

import webserver.response.header.HttpResponseHeader;

public class RedirectHeader implements HttpResponseHeader {

	private String actualView;
	private String header;

	public RedirectHeader(String view) {
		this.actualView = parseViewPath(view);
		createRedirectHeader();
	}

	private String parseViewPath(String view) {
		return view.substring(view.indexOf(":") + 1);
	}

	private void createRedirectHeader() {
		header = "Location: " + actualView + "\r\n";
	}

	@Override
	public String toString() {
		return header;
	}
}
