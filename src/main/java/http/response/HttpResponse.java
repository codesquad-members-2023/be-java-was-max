package http.response;

import http.HttpHeaders;

public class HttpResponse {

	private ResponseLine responseLine;
	private ContentType contentType;
	private HttpHeaders httpHeaders;
	private byte[] body;

	public ResponseLine getResponseLine() {
		return responseLine;
	}

	public void setResponseLine(ResponseLine responseLine) {
		this.responseLine = responseLine;
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

	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
}
