package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import db.Database;
import model.User;

public class RequestBody {
	static final int QUERY_INDEX = 1;
	static final int USER_ID = 0;
	static final int PASSWORD = 1;
	static final int NICKNAME = 2;
	static final int EMAIL = 3;
	public RequestBody(BufferedReader br, int contentLength) throws IOException {
		char[] reader = new char[contentLength];
		br.read(reader, 0, contentLength);
		String[] queries = Arrays.toString(reader).split("&");
		Database.addUser(new User(splitQuery(queries[USER_ID]), splitQuery(queries[PASSWORD]), splitQuery(queries[NICKNAME]), splitQuery(queries[EMAIL])));
	}

	private String splitQuery(String query) {
		return query.split("=")[QUERY_INDEX];
	}
}
