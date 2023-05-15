package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;

import db.Database;
import model.User;

public class HttpRequestUtils {
	static final String HTML_PATH = "src/main/resources/templates";
	static final String STATIC_PATH = "src/main/resources/static";
	static final String HOME_PATH = "src/main/resources/templates/index.html";
	static final int URL_INDEX = 1;
	static final int QUERIES_INDEX = 1;
	static final int QUERY_INDEX = 1;
	static final int USER_ID = 0;
	static final int PASSWORD = 1;
	static final int NICKNAME = 2;
	static final int EMAIL = 3;
	private BufferedReader br;
	private String startLine;
	private String requestHeader;
	private String url;
	private byte[] body;
	private String contentType;
	public HttpRequestUtils(BufferedReader br) throws IOException {
		this.br = br;
		startLine = br.readLine();
		requestHeader = br.readLine();
		url = startLine.split(" ")[URL_INDEX];
	}

	public void debug(Logger logger) throws IOException {
		logger.debug("request line: {}", startLine);
		while (!requestHeader.equals("")) {
			requestHeader = br.readLine();
			logger.debug("{}", requestHeader);
		}
	}

	public void move() throws IOException {
		String[] queries = url.split("\\?");
		if(queries.length > QUERIES_INDEX) {
			String[] query = queries[QUERIES_INDEX].split("&");
			Database.addUser(new User(splitQuery(query[USER_ID]), splitQuery(query[PASSWORD]), splitQuery(query[NICKNAME]), splitQuery(query[EMAIL])));
			body = Files.readAllBytes(new File(HOME_PATH).toPath());
		} else {
			if(url.contains("html")) {
				body = Files.readAllBytes(new File(HTML_PATH + url).toPath());
			}else {
				body = Files.readAllBytes(new File(STATIC_PATH + url).toPath());
			}
		}
		String[] contentTypes = url.split("\\.");
		contentType = contentTypes[contentTypes.length - 1];
	}

	private String splitQuery(String query) {
		return query.split("=")[QUERY_INDEX];
	}

	public byte[] getBody() {
		return body;
	}

	public String getContentType() {
		return contentType;
	}
}
