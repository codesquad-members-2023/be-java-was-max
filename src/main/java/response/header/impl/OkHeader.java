package response.header.impl;

import static utils.HttpResponseUtils.*;

import response.HttpResponseParams;
import response.header.HttpResponseHeader;
import utils.ContentType;

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

