package response;

public class HttpResponseBody {

	private final byte[] body;

	public HttpResponseBody(HttpResponseParams param) {
		this.body = param.getBody();
	}

	public byte[] getBody() {
		return body;
	}
}
