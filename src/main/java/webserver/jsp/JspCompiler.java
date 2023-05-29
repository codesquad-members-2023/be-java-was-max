package webserver.jsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.lineSeparator;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.compile;

public class JspCompiler {

    private static final Logger logger = LoggerFactory.getLogger(JspCompiler.class);
    private static final String JAVA_FILE_STORE_DIRECTORY_PATH = "src/main/java/";

    public static JspServlet service(File jspFile) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        File javaFile = write(jspFile);
        File compiledFile = compileJavaFile(javaFile);
        return createJspServletInstance(compiledFile);
    }

    private static File write(File jspFile) throws IOException {
        String className = jspFile.getName().replace(".", "_");
        String javaFileName = className + ".java"; // twoDice.jsp -> twoDice_jsp
        String javaFilePath = JAVA_FILE_STORE_DIRECTORY_PATH + javaFileName;
        String jspContent = readJspFile(jspFile);
        String javaCode = convertToJava(jspContent, className);
        return writeJavaFile(javaCode, javaFilePath);
    }

    private static String readJspFile(File file) {
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


    private static String convertToJava(String jspContent, String className) {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("import java.io.IOException;").append(lineSeparator());
        javaCode.append("import java.io.OutputStreamWriter;").append(lineSeparator());
        javaCode.append("import http.request.HttpRequest;").append(lineSeparator());
        javaCode.append("import http.response.HttpResponse;").append(lineSeparator());
        javaCode.append("import static http.common.header.EntityHeaderType.*;").append(lineSeparator());

        // Import문 추가
        Pattern importPattern = compile("<%@\\s*page\\s+import=\"(.*?)\"\\s*%>");
        Matcher importMatcher = importPattern.matcher(jspContent);
        while (importMatcher.find()) {
            String imports = importMatcher.group(1);
            String[] importStatements = imports.split("\\s*,\\s*");
            for (String importStatement : importStatements) {
                javaCode.append("import ").append(importStatement).append(";").append(lineSeparator());
            }
        }
        javaCode.append("\n");

        javaCode.append("public class ").append(className).append(" implements webserver.jsp.JspServlet{").append(lineSeparator());

        // 멤버 선언
        Pattern declarationPattern = compile("<%!\\s*(.*?)\\s*%>", DOTALL);
        Matcher declarationMatcher = declarationPattern.matcher(jspContent);
        while (declarationMatcher.find()) {
            String declaration = declarationMatcher.group(1);
            javaCode.append("    ").append(declaration).append(lineSeparator()).append(lineSeparator());
        }

        // 메소드 내부
        javaCode.append("    @Override").append(lineSeparator());
        javaCode.append("    public void init(){").append(lineSeparator());
        javaCode.append("    }").append(lineSeparator()).append(lineSeparator());

        javaCode.append("    @Override").append(lineSeparator());
        javaCode.append("    public void service(HttpRequest request, HttpResponse response) throws IOException {").append(lineSeparator());
        javaCode.append("        response.addHeader(CONTENT_TYPE, \"text/html;charset=utf-8\");").append(lineSeparator());
        javaCode.append("        OutputStreamWriter out = response.getMessageBodyWriter();").append(lineSeparator()).append(lineSeparator());

        Pattern scriptletPattern = compile("<%(?!(\\s*--.*?--\\s*|@|!|=))\\s*(.*?)\\s*%>", DOTALL);
        Matcher scriptletMatcher = scriptletPattern.matcher(jspContent);
        while (scriptletMatcher.find()) {
            String scriptlet = scriptletMatcher.group(2); // (.*?)
            Arrays.stream(scriptlet.split(lineSeparator())).map(String::trim).forEach(s -> {
                javaCode.append("        ").append(s).append(lineSeparator());
            });
        }

        // HTML
        Pattern htmlPattern = compile("(<[^%>]+.*>)|(<%=(.*?)%>)");
        Matcher htmlMatcher = htmlPattern.matcher(jspContent);
        Pattern attributePattern = compile("\"<%=(.*?)%>\"");
        Pattern textPattern = compile("<%=(.*?)%>");

        while (htmlMatcher.find()) {
            String html = htmlMatcher.group(0);
            Matcher attributeMatcher = attributePattern.matcher(html);
            Matcher textMatcher = textPattern.matcher(html);
            if (attributeMatcher.find()) {
                String jsp = attributeMatcher.group(1);
                String result = attributeMatcher.replaceFirst(String.format("\"+%s+\"", jsp));
                result = result.replaceAll("\r\n", "\" + \"");
                javaCode.append("        out.write(\"").append(result).append("\");").append(lineSeparator());
            } else if (textMatcher.find()) {
                String jsp = textMatcher.group(1);
                String result = textMatcher.replaceFirst(String.format("%s", jsp));
                javaCode.append("        out.write(").append(result).append(");").append(lineSeparator());
            } else {
                html = html.replaceAll("\"", "\\\\\"");
                html = html.replaceAll("\r\n", "\" + \"");
                javaCode.append("        out.write(\"").append(html).append("\");").append(lineSeparator());
            }
        }
        javaCode.append("    }").append(lineSeparator());

        javaCode.append("    @Override").append(lineSeparator());
        javaCode.append("    public void destroy(){").append(lineSeparator());
        javaCode.append("    }").append(lineSeparator()).append(lineSeparator());
        javaCode.append("}");
        return javaCode.toString();
    }

    private static File writeJavaFile(String javaCode, String filePath) throws IOException {
        File javaFile = new File(filePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(javaFile));
        writer.write(javaCode);
        writer.close();
        return javaFile;
    }

    private static File compileJavaFile(File javaFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int run = compiler.run(null, null, null, javaFile.getPath());
        logger.debug("run : {}", run);
        return new File(javaFile.getPath().replace(".java", ".class"));
    }

    private static JspServlet createJspServletInstance(File classFile) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        // 클래스 로더 생성
        CustomClassLoader customClassLoader = new CustomClassLoader();
        byte[] classBytes = Files.readAllBytes(classFile.toPath());

        // 클래스 로드
        Class<?> loadedClass = customClassLoader.findClass(null, classBytes, 0, classBytes.length);

        // 인스턴스 생성
        Constructor<?> constructor = loadedClass.getDeclaredConstructor();
        constructor.setAccessible(true); // private 생성자에 접근 가능하도록 설정
        Object instance = constructor.newInstance();

        if (instance instanceof JspServlet) {
            return (JspServlet) instance;
        }
        throw new RuntimeException("webserver.jsp.JspServlet 인스턴스가 아닙니다." + instance);
    }

    static class CustomClassLoader extends ClassLoader {
        public Class<?> findClass(String name, byte[] classBytes, int off, int length) {
            return super.defineClass(name, classBytes, off, length);
        }
    }

}
