package webserver.http.response.component;

import webserver.http.common.HttpStatus;
import webserver.http.common.version.ProtocolVersion;

public class StatusLine {

    private static final String STATUS_LINE_SEPARATOR = " ";

    private final ProtocolVersion protocolVersion;
    private final HttpStatus httpStatus;

    public StatusLine(ProtocolVersion protocolVersion, HttpStatus httpStatus) {
        this.protocolVersion = protocolVersion;
        this.httpStatus = httpStatus;
    }

    public static StatusLine parseStatusLine(String statusLine) {
        String[] statusLineTokens = statusLine.split(STATUS_LINE_SEPARATOR);
        ProtocolVersion protocolVersion = ProtocolVersion.parse(statusLineTokens[0]);
        HttpStatus httpStatus = HttpStatus.resolve(Integer.parseInt(statusLineTokens[1]));
        return new StatusLine(protocolVersion, httpStatus);
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        return String.format("%s %s", protocolVersion, httpStatus);
    }
}
