package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Controller.UserController;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final UserController userController;

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.userController = new UserController();
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			// TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			HttpRequest httpRequest = new HttpRequest(br.readLine());

			logger.debug("start ------------------------------------------");
			logger.debug("httpRequest Method : {}", httpRequest.getMethod());
			logger.debug("httpRequest URL : {}", httpRequest.getURL());
			logger.debug("httpRequest QueryString : {}", httpRequest.getQueryString());
			logger.debug("httpRequest httpVersion : {}", httpRequest.getHttpVersion());
			logger.debug("httpRequest QueryParams : {}", httpRequest.getQueryParams());

			String view = userController.requestMapper(httpRequest);

			new HttpResponse(out, view);
			
			logger.debug("end ------------------------------------------");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
