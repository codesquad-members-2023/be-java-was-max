package webserver.request;

import static utils.HttpRequestUtils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {

	private static final String CONTENT_LENGTH = "Content-Length";
	private Map<String, String> headerParams;

	public HttpRequestHeader(BufferedReader header) throws IOException {
		this.headerParams = new HashMap<>();
		parseHeader(header);
	}

	private void parseHeader(BufferedReader br) throws IOException {
		StringBuilder sb = new StringBuilder();
		String headerLine;
		while (!(headerLine = br.readLine()).equals("")) {
			sb.append(headerLine);
			sb.append("\n");
		}
		headerParams = parseHeaderParams(sb.toString());
	}

	public int getContentLength() {
		if (headerParams.get(CONTENT_LENGTH) == null) {
			return 0;
		}
		return Integer.parseInt(headerParams.get(CONTENT_LENGTH));
	}
}
