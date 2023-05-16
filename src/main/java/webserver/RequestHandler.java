package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import servlet.DispatcherServlet;
import view.View;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final DispatcherServlet dispatcherServlet;
	private final Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.dispatcherServlet = new DispatcherServlet();
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			HttpRequest httpRequest = new HttpRequest(in);

			logHttpRequestInfo(httpRequest);

			View myView = dispatcherServlet.doDispatch(httpRequest);

			HttpResponse httpResponse = new HttpResponse(myView.getViewName(), myView.getBody());

			sendHttpResponse(out, httpResponse);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendHttpResponse(OutputStream out, HttpResponse httpResponse) {
		DataOutputStream dos = new DataOutputStream(out);
		try {
			dos.write(httpResponse.getStatusLine().getBytes());
			dos.write(httpResponse.getHeader().getBytes());
			dos.write(httpResponse.getBody());
			dos.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
