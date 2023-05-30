package servlet.domain.request.target;

// The authority component of a URL, consisting of the domain name and optionally the port (prefixed by a ':'),
// is called the authority form. It is only used with CONNECT when setting up an HTTP tunnel.
// CONNECT developer.mozilla.org:80 HTTP/1.1
public class AuthorityForm {
    private final String domainName;
    private int port;

    public AuthorityForm(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
