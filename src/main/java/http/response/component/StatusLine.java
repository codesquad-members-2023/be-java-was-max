package http.response.component;

import http.common.HttpStatus;
import http.common.version.ProtocolVersion;

public class StatusLine {

    private final ProtocolVersion protocolVersion;
    private final HttpStatus httpStatus;

    public StatusLine(ProtocolVersion protocolVersion, HttpStatus httpStatus) {
        this.protocolVersion = protocolVersion;
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return String.format("%s %s", protocolVersion, httpStatus);
    }
}
