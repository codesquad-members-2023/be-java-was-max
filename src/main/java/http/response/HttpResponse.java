package http.response;

import http.HttpHeaders;

public class HttpResponse {

	private StatusLine statusLine;
	private ContentType contentType;
	private HttpHeaders httpHeaders;
	private byte[] body;

	public HttpResponse() {
		this.httpHeaders = new HttpHeaders();
	}

	public StatusLine getResponseLine() {
		return statusLine;
	}

	public void setResponseLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
}
