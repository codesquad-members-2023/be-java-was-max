package webserver.httpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.ContentType;
import webserver.View;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

	private View myView;

	public HttpResponse(OutputStream out, String view) {
		myView = new View();
		createResponseBody(out, view);
	}

	public void createResponseBody(OutputStream out, String view) {
		DataOutputStream dos = new DataOutputStream(out);
		String contentType = ContentType.getByExtension(extractFileExtensionFromView(view));
		logger.debug("contentType : {}", contentType);

		byte[] body = myView.readResource(view, contentType);

		try {
			response200Header(dos, body.length, contentType);
			responseBody(dos, body);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private String extractFileExtensionFromView(String view) {
		return view.substring(view.lastIndexOf(".") + 1);
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) throws
		IOException {
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
		dos.writeBytes(
			"Content-Type: " + contentType + (contentType.contains("text") ? ";charset=utf-8" : "") + "\r\n");
		dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
		dos.writeBytes("\r\n");
	}

	private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
		dos.write(body, 0, body.length);
		dos.flush();
	}
}
