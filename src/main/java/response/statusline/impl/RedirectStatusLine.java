package response.statusline.impl;

import response.statusline.HttpResponseStatusLine;

public class RedirectStatusLine implements HttpResponseStatusLine {

	private static final String HTTP_VERSION = "HTTP/1.1";
	private static final String HTTP_STATUS_CODE = "302";
	private static final String HTTP_STATUS_MESSAGE = "Found";

	@Override
	public String toString() {
		return HTTP_VERSION + " " + HTTP_STATUS_CODE + " " + HTTP_STATUS_MESSAGE + " " + "\r\n";
	}
}
