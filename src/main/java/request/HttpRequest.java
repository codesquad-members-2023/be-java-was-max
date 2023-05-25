package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import request.exception.HttpRequestParsingException;
import servlet.Model;
import session.Session;

public class HttpRequest {

	private HttpRequestLine httpRequestLine;
	private HttpRequestHeader httpRequestHeader;
	private HttpRequestBody httpRequestBody;
	private Session session;
	private Model model;
	private static final String GET = "GET";

	public HttpRequest(InputStream in) {
		session = new Session();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		try {
			httpRequestLine = new HttpRequestLine(br.readLine());
			httpRequestHeader = new HttpRequestHeader(br, session);
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

	/**
	 * GET 요청일때는 쿼리파라미터를 가져오며
	 * POST 요청일때는 form-data 를 가져온다.
	 * @return
	 */
	public Map<String, String> getParameters() {
		return GET.equals(httpRequestLine.getMethod())
			? httpRequestLine.getQueryParams()
			: httpRequestBody.getFormData();
	}

	public int getContentLength() {
		return httpRequestHeader.getContentLength();
	}

	public String getBody() {
		return httpRequestBody.getBody();
	}

	public Session getSession() {
		return session;
	}

}
