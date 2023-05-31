package util;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;

import static java.lang.System.lineSeparator;

public final class FileUtils {

    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String TEMPLATES_PATH = "src/main/resources/templates";

    private FileUtils() {
        throw new RuntimeException("FileUtils 클래스는 유틸 클래스입니다.");
    }

    /**
     * 파일 경로에 따른 File 객체를 가져옵니다.
     * ex) path=src/main/resources/templates/index.jsp
     */
    public static Optional<File> getFileFromPath(String path) {
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

    /**
     * 파일 경로에 따른 파일이 존재하는지 검사합니다.
     */
    private static boolean isExistFile(String pathname) {
        return new File(pathname).isFile();
    }

    /**
     * 파일을 입력받아 확장자를 제외한 파일 이름을 문자열로 반환합니다.
     * ex) index.jsp => index
     */
    public static String getNameWithoutExtension(File file) {
        String fileName = Path.of(file.toURI()).getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        }
        return fileName;
    }

    public static String readFile(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    public static File writeFile(String filePath, String fileContent) {
        try {
            File javaFile = new File(filePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(javaFile));
            writer.write(fileContent);
            writer.close();
            return javaFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
