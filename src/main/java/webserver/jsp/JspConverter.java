package webserver.jsp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.regex.Pattern.compile;

public class JspConverter {
    private static final Logger logger = LoggerFactory.getLogger(JspConverter.class);

    private static final String JSP_SERVLET_TEMPLATE_FILE_PATH = "src/main/java/webserver/jsp/JspServletTemplate.java";
    private static final String JAVA_STORE_PATH = "src/main/java/webserver/jsp/";
    private static final Pattern JAVA_IMPORT_PATTERN = compile("<%@\\s*page\\s+import=\"(.*?)\"\\s*%>");
    private static final Pattern SCRIPTLET_PATTERN = compile("(<%(?!@|!|\\\\s|=)[\\s\\S]*?%>)");
    private static final Pattern HTML_PATTERN = compile("(<[^%>]+.*>)");
    private static final Pattern VALUE_PATTERN = compile("(<%=.*%>)");
    private final String className;
    private final String jspContent;

    public JspConverter(String className, String jspContent) {
        this.className = className;
        this.jspContent = jspContent;
    }

    public File process() {
        File template = new File(JSP_SERVLET_TEMPLATE_FILE_PATH);

        try {
            // 파일을 읽어서 라인 목록으로 가져옴
            List<String> lines = Files.readAllLines(template.toPath(), UTF_8);

            // 읽어온 라인을 처리하고 수정할 부분을 삽입
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);

                // import문 삽입
                if (line.contains("// IMPORT INSERT CODE HERE")) {
                    String imports = convertToJavaImport(jspContent);
                    lines.add(i + 1, imports);
                }

                // 클래스명 변경
                if (line.contains("class JspServletTemplate")) {
                    line = line.replace("JspServletTemplate", className);
                    lines.set(i, line);
                }

                // 서비스 메소드 코드 삽입
                if (line.contains("// SERVICE METHOD INSERT CODE HERE")) {
                    String services = convertToJavaService(jspContent);
                    lines.add(i + 1, services);
                    break;
                }
            }

            String filePath = JAVA_STORE_PATH + className + ".java";
            String fileContent = String.join(lineSeparator(), lines);
            return FileUtils.writeFile(filePath, fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertToJavaImport(String jspContent) {
        StringBuilder javaCode = new StringBuilder();
        Matcher importMatcher = JAVA_IMPORT_PATTERN.matcher(jspContent);
        while (importMatcher.find()) {
            String imports = importMatcher.group(1);
            String[] importStatements = imports.split("\\s*,\\s*");
            for (String importStatement : importStatements) {
                javaCode.append("import ").append(importStatement).append(";").append(lineSeparator());
            }
        }
        return javaCode.toString();
    }

    private String convertToJavaService(String jspContent) {
        StringBuilder javaCode = new StringBuilder();

        Pattern scriptletOrHtmlPattern = compile(SCRIPTLET_PATTERN.pattern() + "|" + HTML_PATTERN.pattern() + "|" + VALUE_PATTERN.pattern());
        Matcher scriptletOrHtmlMatcher = scriptletOrHtmlPattern.matcher(jspContent);
        while (scriptletOrHtmlMatcher.find()) {
            String scriptlet = scriptletOrHtmlMatcher.group(0);
            Matcher scriptletMatcher = SCRIPTLET_PATTERN.matcher(scriptlet);
            Matcher htmlMatcher = HTML_PATTERN.matcher(scriptlet);
            Matcher valueMatcher = VALUE_PATTERN.matcher(scriptlet);

            logger.debug("scriptlet : {}", scriptlet);

            if (scriptletMatcher.matches()) {
                String scriptletTag = scriptletMatcher.group(0);
                scriptletTag = scriptletTag.replaceAll("<%|%>", "");
                Arrays.stream(scriptletTag.split(lineSeparator())).map(String::trim).forEach(s -> {
                    javaCode.append("        ").append(s).append(lineSeparator());
                });
            } else if (htmlMatcher.matches()) {
                String htmlTag = htmlMatcher.group(0)
                        .replaceAll("\"", "\\\\\"")
                        .replaceAll(lineSeparator(), "")
                        .replaceAll("<%=", "\"+")
                        .replaceAll("%>", "+\"");
                logger.debug("htmlTag : {}", htmlTag);
                javaCode.append("        ").append("out.write(\"").append(htmlTag).append("\");").append(lineSeparator());
            } else if (valueMatcher.matches()) {
                String value = valueMatcher.group(0)
                        .replaceAll("<%=|%>", "");
                javaCode.append("        ").append("out.write(").append(value).append(");").append(lineSeparator());
            }
        }
        return javaCode.toString();
    }
}
