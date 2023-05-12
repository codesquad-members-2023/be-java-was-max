package http.response;

public class ResponseLine {

	private final String httpVersion;
	private final int statusCode;
	private final String statusMessage;

	public ResponseLine(String httpVersion, int statusCode, String statusMessage) {
		this.httpVersion = httpVersion;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return httpVersion + " " + statusCode + " " + statusMessage;
	}
}
