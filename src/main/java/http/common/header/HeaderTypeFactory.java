package http.common.header;

import java.util.Optional;

public final class HeaderTypeFactory {

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
        Optional<ResponseHeaderType> optionalResponseHeaderType = ResponseHeaderType.resolve(headerName);
        return optionalResponseHeaderType.orElseThrow();
    }
}

