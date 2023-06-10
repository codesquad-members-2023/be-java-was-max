package servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Article;
import session.Session;

public class Model {
	private Map<String, Object> requestAttributes;

	public Model() {
		this.requestAttributes = new HashMap<>();
	}

	public void setSession(Session session) {
		requestAttributes.put("loginUserExist", session.isExist());
		requestAttributes.put("session", session);
	}

	public void setAttribute(String key, Object value) {
		requestAttributes.put(key, value);
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
		return String.valueOf(requestAttributes.get(key));
	}

	public String getValue(String listKey, String valueKey) {
		List<Article> list = getListTypeValue(listKey);

		return null;
	}
}
