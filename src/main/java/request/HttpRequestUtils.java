package request;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;

public class HttpRequestUtils {
	private static final int CONTENT_LENGTH_INDEX = 1;
	private BufferedReader br;
	private RequestLine requestLine;
	private int contentLength;
	public HttpRequestUtils(BufferedReader br) throws IOException {
		this.br = br;
		requestLine = new RequestLine(br.readLine());
	}

	public void debug(Logger logger) throws IOException {
		logger.debug("request line: {}", requestLine.getRequestLine());
		String requestHeader;
		while (!(requestHeader = br.readLine()).equals("")) {
			logger.debug("{}", requestHeader);
			if(requestHeader.contains("Content-Length")) {
				contentLength = Integer.parseInt(requestHeader.split(" ")[CONTENT_LENGTH_INDEX]);
			}
		}
	}

	public void processRequestBody() throws IOException {
		if(contentLength > 0) {
			new RequestBody(br, contentLength);
		}
	}

	public RequestLine getRequestLine() {
		return requestLine;
	}
}
