package webserver;

import config.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;
import webserver.frontcontroller.ModelAndView;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

public class TemplateEngineParser {
    private static final Logger logger = LoggerFactory.getLogger(TemplateEngineParser.class);

    private static final Pattern INCLUDE_PATTERN = Pattern.compile("\\{\\{fn-include:\\s?(.*?)\\}\\}");
    private static final Pattern PRINT_PATTERN = Pattern.compile("\\{\\{fn-print:\\s?(.*?)\\}\\}");
    private static final Pattern IF_NOT_NULL_PATTERN = Pattern.compile("\\{\\{fn-ifNotNull:\\s?(.*?)\\}\\}([\\s\\S]*?)\\{\\{/fn-ifNotNull\\}\\}");
    private static final Pattern IF_NULL_PATTERN = Pattern.compile("\\{\\{fn-ifNull:\\s?(.*?)\\}\\}([\\s\\S]*?)\\{\\{/fn-ifNull\\}\\}");
    private static final Pattern FOREACH_LIST_PATTERN = Pattern.compile("\\{\\{fn-forEachList:\\s*(.*?)\\}\\}([\\s\\S]*?)\\{\\{/fn-forEachList\\}\\}");
    private static final Pattern GET_STRING_PATTERN = Pattern.compile("\\{\\{fn-get:\\s?(.*?)\\}\\}");
    private static final Pattern OBJECT_GET_STRING_PATTERN = Pattern.compile("\\{\\{fn-obj-get:\\s?(.*?)\\s(.*?)\\}\\}");

    private static TemplateEngineParser instance;

    public static TemplateEngineParser getInstance() {
        if (instance == null) {
            instance = new TemplateEngineParser();
        }
        return instance;
    }

    public byte[] parseHtmlDynamically(Path resource, ModelAndView modelAndView) {
        Optional<File> optionalFile = FileUtils.getFileFromPath(resource);
        if (optionalFile.isPresent()) {
            File htmlFile = optionalFile.get();
            String html = FileUtils.readFile(htmlFile);
            return replaceTemplateTag(html, modelAndView).getBytes(UTF_8);
        }
        return new byte[0];
    }

    private String replaceTemplateTag(String html, ModelAndView modelAndView) {
        html = includeHtml(html);
        html = replaceIfNotNullTag(html, modelAndView);
        html = replaceIfNullTag(html, modelAndView);
        html = replacePrintStringTag(html, modelAndView);
        html = replaceForEachList(html, modelAndView);
        html = replaceObjGet(html, modelAndView);
        return html;
    }

    private String includeHtml(String html) {
        WebConfig webConfig = WebConfig.getInstance();
        Matcher matcher = INCLUDE_PATTERN.matcher(html);

        while (matcher.find()) {
            Path path = Paths.get(webConfig.getTemplatesResourcePath() + matcher.group(1));
            Optional<File> optionalFile = FileUtils.getFileFromPath(path);

            if (optionalFile.isPresent()) {
                String include = FileUtils.readFile(optionalFile.get());
                String regex = replaceMetaToEscape(matcher.group(0));
                html = html.replaceAll(regex, include);
            }
        }
        return html;
    }

    private String replaceIfNotNullTag(String html, ModelAndView modelAndView) {
        Matcher matcher = IF_NOT_NULL_PATTERN.matcher(html);

        while (matcher.find()) {
            if (modelAndView.containsAttribute(matcher.group(1))) {
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), matcher.group(2));
            } else {
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), "");
            }
        }
        return html;
    }


    private String replaceIfNullTag(String html, ModelAndView modelAndView) {
        Matcher matcher = IF_NULL_PATTERN.matcher(html);

        while (matcher.find()) {
            if (modelAndView.containsAttribute(matcher.group(1))) {
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), "");
            } else {
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), matcher.group(2));
            }
        }
        return html;
    }

    private String replacePrintStringTag(String html, ModelAndView modelAndView) {
        Matcher matcher = PRINT_PATTERN.matcher(html);

        while (matcher.find()) {
            if (modelAndView.containsAttribute(matcher.group(1))) {
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), String.valueOf(modelAndView.getAttribute(matcher.group(1))));
            } else {
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), "");
            }
        }
        return html;
    }

    private String replaceForEachList(String html, ModelAndView modelAndView) {
        Matcher matcher = FOREACH_LIST_PATTERN.matcher(html);

        while (matcher.find()) {
            if (modelAndView.containsAttribute(matcher.group(1)) &&
                    modelAndView.getAttribute(matcher.group(1)) instanceof List) {
                List<?> list = (List<?>) modelAndView.getAttribute(matcher.group(1));
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), forEach(matcher.group(2), list));
            }
        }
        return html;
    }

    private String forEach(String tagBlock, List<?> list) {
        StringBuilder sb = new StringBuilder();

        for (Object object : list) {
            sb.append(replaceGet(tagBlock, object)).append(lineSeparator());
        }
        return sb.toString();
    }

    private String replaceGet(String tagBlock, Object object) {
        Matcher matcher = GET_STRING_PATTERN.matcher(tagBlock);

        while (matcher.find()) {
            tagBlock = tagBlock.replaceAll(replaceMetaToEscape(matcher.group(0)), getMemberVariable(matcher.group(1), object));
        }
        return tagBlock;
    }

    public String replaceObjGet(String html, ModelAndView modelAndView) {
        Matcher matcher = OBJECT_GET_STRING_PATTERN.matcher(html);
        while (matcher.find()) {
            if (modelAndView.containsAttribute(matcher.group(1))) {
                Object instance = modelAndView.getAttribute(matcher.group(1));
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), getMemberVariable(matcher.group(2), instance));
            } else {
                html = html.replaceAll(replaceMetaToEscape(matcher.group(0)), "");
            }
        }
        return html;
    }

    private String getMemberVariable(String variableName, Object object) {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (parseVariableNameFromGetterMethodName(method.getName()).equals(variableName)) {
                try {
                    return String.valueOf(method.invoke(object));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("메소드를 실행할 수 없습니다. : " + method.getName());
                }
            }
        }
        return "";
    }

    private String parseVariableNameFromGetterMethodName(String getterMethodName) {
        String substringName = getterMethodName.substring(3);
        return Character.toLowerCase(substringName.charAt(0)) + substringName.substring(1);
    }

    // {{fn-include: /include/head.html}} => \{\{fn-include: /include/head\.html\}\}
    private String replaceMetaToEscape(String meta) {
        String pattern = "[{}\\[\\]().*+?^$|\\\\]";
        return meta.replaceAll(pattern, "\\\\$0");
    }
}
