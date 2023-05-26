package response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ViewPathResolver {
    private static final String EXTENSION_SEPARATOR = ".";

    public String resolveMimeType(String requestURI) {
        ContentType contentType = getContentType(requestURI);
        return contentType.getMimeType();
    }

    public byte[] readViewFile(String requestURI) throws IOException {
        String viewPath = resolvePath(requestURI);
        return Files.readAllBytes(Paths.get(viewPath));
    }

    private String resolvePath(String requestURI) {
        ContentType contentType = getContentType(requestURI);
        return contentType.getPath() + requestURI;
    }

    public ContentType getContentType(String requestURI) {
        try {
            String requestedExtension = getFileExtension(requestURI);
            return Arrays.stream(ContentType.values())
                    .filter(contentType -> contentType.extension.equals(requestedExtension))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("일치하는 파일 확장자 없음: {}" + requestURI));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 요청 URI: {}" + requestURI, e);
        }
    }

    private String getFileExtension(String requestURI) {
        return requestURI.substring(requestURI.lastIndexOf(EXTENSION_SEPARATOR) + 1);
    }
}
