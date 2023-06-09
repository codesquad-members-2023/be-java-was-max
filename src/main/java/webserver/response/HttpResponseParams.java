package webserver.response;

import session.Session;

public class HttpResponseParams {

	private String viewName;
	private byte[] body;
	private Session session;

	public HttpResponseParams(String viewName, byte[] body, Session session) {
		this.viewName = viewName;
		this.body = body;
		this.session = session;
	}

	public String getViewName() {
		return viewName;
	}

	public byte[] getBody() {
		return body;
	}

	public Session getSession() {
		return session;
	}
}
