package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestHeader {

	private int contentLength;

	private static final String CONTENT_LENGTH = "Content-Length";

	private static final int CONTENT_LENGTH_PREFIX_LENGTH = 16;

	public HttpRequestHeader(BufferedReader header) throws IOException {
		parseHeader(header);
	}

	private void parseHeader(BufferedReader br) throws IOException {
		String headerLine;
		while (!(headerLine = br.readLine()).equals("")) {
			extractContentLength(headerLine);
		}
	}

	/**
	 * contentLength를 header에서 가져오는 작업을 하는 메서드이다.
	 * @param headerLine
	 */
	private void extractContentLength(String headerLine) {
		if (headerLine.startsWith(CONTENT_LENGTH)) {
			contentLength = Integer.parseInt(headerLine.substring(CONTENT_LENGTH_PREFIX_LENGTH));
		}
	}

	public int getContentLength() {
		return contentLength;
	}
}
