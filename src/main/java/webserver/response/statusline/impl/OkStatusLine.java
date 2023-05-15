package webserver.response.statusline.impl;

import webserver.response.statusline.HttpResponseStatusLine;

public class OkStatusLine implements HttpResponseStatusLine {

	private static final String HTTP_VERSION = "HTTP/1.1";
	private static final String HTTP_STATUS_CODE = "200";
	private static final String HTTP_STATUS_MESSAGE = "OK";

	@Override
	public String toString() {
		return HTTP_VERSION + " " + HTTP_STATUS_CODE + " " + HTTP_STATUS_MESSAGE + " " + "\r\n";
	}
}
