package servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import request.HttpRequest;
import session.Session;

public class Model {
	private final Map<String, Object> requestAttributes;

	public Model(HttpRequest request) {
		this.requestAttributes = new HashMap<>(
			Map.of("loginUserExist", request.getSession().isExist(), "session", request.getSession()));
	}

	public Session getSession() {
		return (Session)requestAttributes.get("session");
	}

	public boolean getBoolTypeValue(String key) {
		return (boolean)requestAttributes.get(key);
	}

	public List getListTypeValue(String key) {
		return (List)requestAttributes.get(key);
	}

	public String getStringTypeValue(String key) {
		return (String)requestAttributes.get(key);
	}
}
