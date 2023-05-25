package auth;

public class Cookie {
    private final String cookieName;
    private final String sessionId;

    public Cookie(String cookieName, String sessionId) {
        this.cookieName = cookieName;
        this.sessionId = sessionId;
    }

    public String getValue(){
        return sessionId;
    }

    public String getName(){
        return cookieName;
    }
}
