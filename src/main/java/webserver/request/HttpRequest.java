package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import webserver.request.exception.HttpRequestParsingException;

public class HttpRequest {

	private HttpRequestLine httpRequestLine;
	private HttpRequestHeader httpRequestHeader;
	private HttpRequestBody httpRequestBody;
	private static final String GET = "GET";

	public HttpRequest(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		try {
			httpRequestLine = new HttpRequestLine(br.readLine());
			httpRequestHeader = new HttpRequestHeader(br);
			httpRequestBody = new HttpRequestBody(br, httpRequestHeader.getContentLength());
		} catch (IOException e) {
			throw new HttpRequestParsingException();
		}
	}

	public String getMethod() {
		return httpRequestLine.getMethod();
	}

	public String getURL() {
		return httpRequestLine.getURL();
	}

	public Map<String, String> getQueryParams() {
		return GET.equals(httpRequestLine.getMethod())
			? httpRequestLine.getQueryParams()
			: httpRequestBody.getQueryParams();
	}

	public int getContentLength() {
		return httpRequestHeader.getContentLength();
	}

	public String getBody() {
		return httpRequestBody.getBody();
	}
}
