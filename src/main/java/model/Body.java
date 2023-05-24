package model;

import util.RequestParser;

import java.io.UnsupportedEncodingException;

public class Body {

    private final String body;

    public Body(String rawBody) throws UnsupportedEncodingException {
        this.body = RequestParser.decodeBody(rawBody);
    }

    public String getBody() {
        return body;
    }
}
