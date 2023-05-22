package webserver.request;

import static utils.HttpRequestUtils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import session.Session;

public class HttpRequestHeader {

	private static final String CONTENT_LENGTH = "Content-Length";
	private Map<String, String> headerParams;
	private final Session session;

	public HttpRequestHeader(BufferedReader header, Session session) throws IOException {
		this.headerParams = new HashMap<>();
		this.session = session;
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
		setSession();
	}

	public int getContentLength() {
		if (headerParams.get(CONTENT_LENGTH) == null) {
			return 0;
		}
		return Integer.parseInt(headerParams.get(CONTENT_LENGTH));
	}

	/**
	 * 만약 header에 Cookie값이 있다면 session 객체의 UUID필드를 초기화해준다.
	 */
	private void setSession() {
		String cookieValue = headerParams.get("Cookie");
		if (cookieValue != null) {
			String[] cookieParser = cookieValue.split("=");
			session.setUUID(cookieParser[1]);
		}
	}
}
