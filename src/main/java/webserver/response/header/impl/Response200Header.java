package webserver.response.header.impl;

import utils.ContentType;
import webserver.response.header.HttpResponseHeader;

public class Response200Header implements HttpResponseHeader {

	private String contentType;
	private int contentLength;
	private String header;

	public Response200Header(String view, int contentLength) {
		this.contentType = ContentType.getByExtension(extractFileExtensionFromView(view));
		this.contentLength = contentLength;
		createResponse200Header();
	}

	private String extractFileExtensionFromView(String view) {
		return view.substring(view.lastIndexOf(".") + 1);
	}

	private void createResponse200Header() {
		header = "Content-Type: " + contentType + (contentType.contains("text") ? ";charset=utf-8" : "") + "\r\n" +
			"Content-Length: " + contentLength + "\r\n" +
			"\r\n";
	}

	@Override
	public String toString() {
		return header;
	}
}

