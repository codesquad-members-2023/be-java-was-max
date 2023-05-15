package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Controller.UserController;
import webserver.httpRequest.HttpRequest;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final UserController userController;
	private final Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.userController = new UserController();
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			HttpRequest httpRequest = new HttpRequest(in);

			logger.debug("start ------------------------------------------");
			logger.debug("httpRequest Method : {}", httpRequest.getMethod());
			logger.debug("httpRequest URL : {}", httpRequest.getURL());
			logger.debug("httpRequest QueryParams: {}", httpRequest.getQueryParams());
			logger.debug("httpRequest getContentLength : {}", httpRequest.getContentLength());
			logger.debug("httpRequest body : {}", httpRequest.getBody());

			String view = userController.requestMapper(httpRequest);

			new HttpResponse(out, view);

			logger.debug("end ------------------------------------------");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
