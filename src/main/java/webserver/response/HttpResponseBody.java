package webserver.response;

public class HttpResponseBody {

	private final byte[] body;

	public HttpResponseBody(byte[] body) {
		this.body = body;
	}

	public byte[] getBody() {
		return body;
	}
}
