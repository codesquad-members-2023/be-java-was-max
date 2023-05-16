package servlet.domain;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String url;
    private final Map<String, String> parameters;
    private final String httpVersion;
    private final String host;
    private final String connection;
    private final String cacheControl;
    private final String secChUa;
    private final String dnt;
    private final String secChUaMobile;
    private final String userAgent;
    private final String secChUaPlatform;
    private final String accept;
    private final String acceptP;
    private final String secFetchSite;
    private final String secFetchMode;
    private final String secFetchDest;
    private final String secFetchUser;
    private final String upgradeInsecureRequests;
    private final String referer;
    private final String acceptEncoding;
    private final String acceptLanguage;
    private final String cookie;

    private HttpRequest(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.httpVersion = builder.httpVersion;
        this.host = builder.host;
        this.connection = builder.connection;
        this.cacheControl = builder.cacheControl;
        this.secChUa = builder.secChUa;
        this.dnt = builder.dnt;
        this.secChUaMobile = builder.secChUaMobile;
        this.userAgent = builder.userAgent;
        this.secChUaPlatform = builder.secChUaPlatform;
        this.accept = builder.accept;
        this.acceptP = builder.acceptP;
        this.secFetchSite = builder.secFetchSite;
        this.secFetchMode = builder.secFetchMode;
        this.secFetchDest = builder.secFetchDest;
        this.referer = builder.referer;
        this.acceptEncoding = builder.acceptEncoding;
        this.acceptLanguage = builder.acceptLanguage;
        this.upgradeInsecureRequests = builder.upgradeInsecureRequests;
        this.secFetchUser = builder.secFetchUser;
        this.cookie = builder.cookie;
        this.parameters = builder.parameters;
    }

    public String getAccept() {
        return accept;
    }

    public String getSecFetchUser() {
        return secFetchUser;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getHost() {
        return host;
    }

    public String getConnection() {
        return connection;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public String getSecChUa() {
        return secChUa;
    }

    public String getDnt() {
        return dnt;
    }

    public String getSecChUaMobile() {
        return secChUaMobile;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getSecChUaPlatform() {
        return secChUaPlatform;
    }

    public String getAcceptType() {
        return accept;
    }

    public String getUpgradeInsecureRequests() {
        return upgradeInsecureRequests;
    }

    public String getAcceptP() {
        return acceptP;
    }

    public String getSecFetchSite() {
        return secFetchSite;
    }

    public String getSecFetchMode() {
        return secFetchMode;
    }

    public String getSecFetchDest() {
        return secFetchDest;
    }

    public String getReferer() {
        return referer;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public String getCookie() {
        return cookie;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public static class Builder {
        private String method;
        private String url;
        private String httpVersion;
        private String host;
        private String connection;
        private String cacheControl;
        private String secChUa;
        private String dnt;
        private String secChUaMobile;
        private String userAgent;
        private String secChUaPlatform;
        private String accept;
        private String acceptP;
        private String secFetchSite;
        private String secFetchMode;
        private String secFetchDest;
        private String upgradeInsecureRequests;
        private String referer;
        private String acceptEncoding;
        private String acceptLanguage;
        private String secFetchUser;
        private String cookie;
        private Map<String, String> parameters = new HashMap<>();

        public Builder() {
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder cookie(String cookie) {
            this.cookie = cookie;
            return this;
        }

        public Builder parameters(Map<String, String> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder httpVersion(String httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public Builder secFetchUser(String secFetchUser) {
            this.secFetchUser = secFetchUser;
            return this;
        }

        public Builder upgradeInsecureRequests(String upgradeInsecureRequests) {
            this.upgradeInsecureRequests = upgradeInsecureRequests;
            return this;
        }


        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder connection(String connection) {
            this.connection = connection;
            return this;
        }

        public Builder cacheControl(String cacheControl) {
            this.cacheControl = cacheControl;
            return this;
        }

        public Builder secChUa(String secChUa) {
            this.secChUa = secChUa;
            return this;
        }

        public Builder dnt(String dnt) {
            this.dnt = dnt;
            return this;
        }

        public Builder secChUaMobile(String secChUaMobile) {
            this.secChUaMobile = secChUaMobile;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder secChUaPlatform(String secChUaPlatform) {
            this.secChUaPlatform = secChUaPlatform;
            return this;
        }

        public Builder accept(String acceptType) {
            this.accept = acceptType;
            return this;
        }

        public Builder acceptP(String acceptP) {
            this.acceptP = acceptP;
            return this;
        }

        public Builder secFetchSite(String secFetchSite) {
            this.secFetchSite = secFetchSite;
            return this;
        }

        public Builder secFetchMode(String secFetchMode) {
            this.secFetchMode = secFetchMode;
            return this;
        }

        public Builder secFetchDest(String secFetchDest) {
            this.secFetchDest = secFetchDest;
            return this;
        }

        public Builder referer(String referer) {
            this.referer = referer;
            return this;
        }

        public Builder acceptEncoding(String acceptEncoding) {
            this.acceptEncoding = acceptEncoding;
            return this;
        }

        public Builder acceptLanguage(String acceptLanguage) {
            this.acceptLanguage = acceptLanguage;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
