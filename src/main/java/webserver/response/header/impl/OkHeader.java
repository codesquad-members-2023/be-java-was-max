package webserver.response.header.impl;

import static utils.HttpResponseUtils.*;

import utils.ContentType;
import webserver.response.HttpResponseParams;
import webserver.response.header.HttpResponseHeader;

public class OkHeader implements HttpResponseHeader {

	private String contentType;
	private int contentLength;
	private String header;

	public OkHeader(HttpResponseParams httpResponseParams) {
		this.contentType = ContentType.getByExtension(extractFileExtensionFromView(httpResponseParams.getViewName()));
		this.contentLength = httpResponseParams.getBody().length;
		createHeader();
	}

	private void createHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("Content-Type: ").append(contentType).append("\r\n")
			.append("Content-Length: ").append(contentLength).append("\r\n")
			.append("\r\n");
		header = sb.toString();

	}

	@Override
	public String toString() {
		return header;
	}
}

