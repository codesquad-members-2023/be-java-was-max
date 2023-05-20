package util;

import java.io.File;
import java.util.Optional;

public final class FileUtils {

    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String TEMPLATES_PATH = "src/main/resources/templates";

    private FileUtils() {

    }

    public static Optional<File> readFile(String path) {
        // static 디렉토리 탐색
        if (isExistFile(STATIC_PATH + path)) {
            return Optional.of(new File(STATIC_PATH + path));
        }
        // template 디렉토리 탐색
        if (isExistFile(TEMPLATES_PATH + path)) {
            return Optional.of(new File(TEMPLATES_PATH + path));
        }
        return Optional.empty();
    }

    private static boolean isExistFile(String pathname) {
        return new File(pathname).isFile();
    }
}
