package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Controller.UserController;
import view.View;
import view.impl.OkResponseView;
import view.impl.RedirectResponseView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

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

			logHttpRequestInfo(httpRequest);

			String view = userController.requestMapper(httpRequest);
			View myView = initView(view);

			HttpResponse httpResponse = new HttpResponse(view, myView.getBody());

			DataOutputStream dos = new DataOutputStream(out);
			sendHttpResponse(dos, httpResponse);

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendHttpResponse(DataOutputStream dos, HttpResponse httpResponse) {
		try {
			dos.write(httpResponse.toString().getBytes());
			dos.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public View initView(String view) {
		if (view.startsWith("redirect")) {
			return new RedirectResponseView(view);
		}
		return new OkResponseView(view);
	}

	private void logHttpRequestInfo(HttpRequest httpRequest) {
		logger.debug("start ------------------------------------------");
		logger.debug("httpRequest Method : {}", httpRequest.getMethod());
		logger.debug("httpRequest URL : {}", httpRequest.getURL());
		logger.debug("httpRequest QueryParams: {}", httpRequest.getQueryParams());
		logger.debug("httpRequest getContentLength : {}", httpRequest.getContentLength());
		logger.debug("httpRequest body : {}", httpRequest.getBody());
		logger.debug("end ------------------------------------------");
	}
}
