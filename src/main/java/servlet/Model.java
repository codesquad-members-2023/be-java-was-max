package servlet;

import java.util.HashMap;
import java.util.Map;

import session.Session;
import webserver.request.HttpRequest;

public class Model {
	private final Map<String, Object> map;

	public Model(HttpRequest request) {
		this.map = new HashMap<>(Map.of("session", request.getSession()));
	}

	public Session getSession() {
		return (Session)map.get("session");
	}
}
