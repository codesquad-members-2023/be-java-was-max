package request;

public class RequestLine {
	private static final int METHOD_INDEX = 0;
	private static final int URL_INDEX = 1;
	private String requestLine;
	private String httpMethod;
	private String url;
	private String contentType;
	public RequestLine(String requestLine) {
		this.requestLine = requestLine;
		String[] requestLines = requestLine.split(" ");
		httpMethod = requestLines[METHOD_INDEX];
		url = requestLines[URL_INDEX];
		String[] contentTypes = url.split("\\.");
		contentType = ContentType.getContentType((contentTypes[contentTypes.length - 1]));
	}

	public String getRequestLine() {
		return requestLine;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getUrl() {
		return url;
	}

	public String getContentType() {
		return contentType;
	}
}
