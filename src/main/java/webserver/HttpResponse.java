package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.ContentType;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

	private View view;

	public HttpResponse(OutputStream out, String URL) {
		view = new View();
		createResponseBody(out, URL);
	}

	public void createResponseBody(OutputStream out, String viewPath) {
		DataOutputStream dos = new DataOutputStream(out);
		String contentType = ContentType.getByExtension(extractFileExtensionFromURL(viewPath));
		logger.debug("contentType : {}", contentType);

		byte[] body = view.readResource(viewPath, contentType);

		response200Header(dos, body.length, contentType);
		responseBody(dos, body);
	}

	private String extractFileExtensionFromURL(String URL) {
		String[] tokens = URL.split("\\.");
		return tokens[tokens.length - 1];
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes(
				"Content-Type: " + contentType + (contentType.contains("text") ? ";charset=utf-8" : "") + "\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
