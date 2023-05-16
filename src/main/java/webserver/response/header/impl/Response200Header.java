package webserver.response.header.impl;

import static utils.HttpResponseUtils.*;

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

