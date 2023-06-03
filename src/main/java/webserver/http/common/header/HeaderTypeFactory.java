package webserver.http.common.header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class HeaderTypeFactory {

    private static final Logger logger = LoggerFactory.getLogger(HeaderTypeFactory.class);


    private HeaderTypeFactory() {

    }

    public static HeaderType createHeaderType(String headerName) {
        Optional<EntityHeaderType> optionalEntityHeaderType = EntityHeaderType.resolve(headerName);
        if (optionalEntityHeaderType.isPresent()) {
            return optionalEntityHeaderType.get();
        }
        Optional<GeneralHeaderType> optionalGeneralHeaderType = GeneralHeaderType.resolve(headerName);
        if (optionalGeneralHeaderType.isPresent()) {
            return optionalGeneralHeaderType.get();
        }
        Optional<RequestHeaderType> optionalRequestHeaderType = RequestHeaderType.resolve(headerName);
        if (optionalRequestHeaderType.isPresent()) {
            return optionalRequestHeaderType.get();
        }
        return ResponseHeaderType.resolve(headerName).orElse(null);
    }
}

