package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestBuilder;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.DispatcherServlet;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final DispatcherServlet DISPATCHER_SERVLET = new DispatcherServlet();

	private final Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
				connection.getPort());

		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		     OutputStream out = connection.getOutputStream()) {
			HttpRequest httpRequest = HttpRequestBuilder.build(br);
			HttpResponse httpResponse = new HttpResponse();

			DISPATCHER_SERVLET.dispatch(httpRequest, httpResponse);

			logger.debug(httpRequest.toString());

			DataOutputStream dos = new DataOutputStream(out);
			response200Header(dos, httpResponse);
			responseBody(dos, httpResponse.getBody());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void response200Header(DataOutputStream dos, HttpResponse httpResponse) {
		try {
			dos.writeBytes(httpResponse.getResponseLine().toString() + " \r\n");
			dos.writeBytes("Content-Type: " + httpResponse.getContentType().getValue() + "\r\n");
			dos.writeBytes("Content-Length: " + httpResponse.getBody().length + "\r\n");
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
