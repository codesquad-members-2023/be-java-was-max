package webserver.jsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static java.nio.file.Files.readAllBytes;
import static util.FileUtils.getNameWithoutExtension;
import static util.FileUtils.readFile;

public class JspCompiler {

    private static final Logger logger = LoggerFactory.getLogger(JspCompiler.class);
    private static final String JAVA_FILE_EXTENSION = ".java";
    private static final String CLASS_FILE_EXTENSION = ".class";

    /**
     * JspCompilter는 다음과 같은 과정을 수행합니다.
     * 1. jsp 파일 변환하여 자바 소스코드 파일 생성
     * 2. 자바 소스코드 파일 컴파일하여 .class 파일 생성
     * 3. .class 파일 기반으로 JspServlet 인스턴스 생성하여 반환
     */
    public static JspServlet service(File jspFile) {
        File javaFile = writeJavaFile(jspFile);
        File compiledFile = compileJavaFile(javaFile);
        return createJspServletInstance(compiledFile);
    }

    /**
     * jsp 파일을 변환하여 자바 소스 파일을 작성합니다.
     */
    private static File writeJavaFile(File jspFile) {
        String className = getNameWithoutExtension(jspFile);
        String jspContent = readFile(jspFile);
        return new JspConverter(className, jspContent).process();
    }

    /**
     * 자바 파일 컴파일
     */
    private static File compileJavaFile(File javaFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int run = compiler.run(null, null, null, javaFile.getPath());

        if (run == 0) {
            logger.debug("컴파일 완료 : {}", javaFile.getName());
        } else {
            throw new RuntimeException("컴파일 실패 : " + javaFile.getName());
        }
        return new File(javaFile.getPath().replace(JAVA_FILE_EXTENSION, CLASS_FILE_EXTENSION));
    }

    /**
     * 클래스(.class) 파일에 대한 인스턴스를 생성하여 반환
     */
    private static JspServlet createJspServletInstance(File classFile) {
        try {
            // 클래스 로더 생성
            JspClassLoader jspClassLoader = new JspClassLoader();

            // 클래스 로드
            byte[] bytes = readAllBytes(classFile.toPath());
            Class<?> loadedClass = jspClassLoader.findClass(null, bytes, 0, bytes.length);

            // 인스턴스 생성
            Constructor<?> constructor = loadedClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();

            if (instance instanceof JspServlet) {
                return (JspServlet) instance;
            }
            throw new RuntimeException("JspServlet 인스턴스가 아닙니다. : " + instance);
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static class JspClassLoader extends ClassLoader {
        public Class<?> findClass(String name, byte[] classBytes, int off, int length) {
            return super.defineClass(name, classBytes, off, length);
        }
    }

}
