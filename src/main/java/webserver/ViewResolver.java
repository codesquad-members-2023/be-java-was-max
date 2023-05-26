package webserver;

import http.HttpResponse;
import util.RequestUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Collection;

public class ViewResolver {

    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String TEMPLATE_START_REGEX = "\\{\\{.+}}";
    private static final String TEMPLATE_END_REGEX = "\\{\\{/\\w+}}";

    public static void resolve(ModelAndView modelAndView, HttpResponse httpResponse) throws IOException, IllegalAccessException {
        String view = modelAndView.getView();
        if (view.startsWith(REDIRECT_PREFIX)) {
            httpResponse.setStatus(302, "FOUND");
            httpResponse.setRedirectHeader(view.replace(REDIRECT_PREFIX, ""));
        } else {
            if (view.endsWith(".html")) {
                httpResponse.setBody(generateDynamicBody(view, modelAndView.getModel()));
            } else {
                httpResponse.setBody(generateStaticBody(view));
            }
            httpResponse.setHeaders(view);
        }
    }

    private static byte[] generateDynamicBody(String view, Model model) throws IOException, IllegalAccessException {
        BufferedReader br = new BufferedReader(new FileReader(RequestUtil.findResource(view)));
        StringBuilder body = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            if (line.trim().matches(TEMPLATE_START_REGEX)) {
                processTemplate(line, br, model, body);
            } else {
                body.append(line);
            }
        }
        br.close();
        return body.toString().getBytes();
    }

    private static void processTemplate(String line, BufferedReader br, Model model, StringBuilder body) throws IOException, IllegalAccessException {
        String[] identifier = line.trim().replaceAll("\\{\\{|}}", "").split(":");
        String contents = collectContents(br);

        if (identifier[0].equals("for")) {
            Collection<Object> list = (Collection<Object>)model.getAttribute(identifier[1]);
            for (Object object : list) {
                Class<?> cls = object.getClass();
                String replaceContents = contents;
                for(Field field : cls.getDeclaredFields()) {
                    field.setAccessible(true);
                    replaceContents = replaceContents.replace("{{" + field.getName() + "}}", (String)field.get(object));
                }
                body.append(replaceContents);
            }
        } else {
            contents = replaceAttribute(model, contents);
            if (identifier[0].equals("if")) {
                if ((boolean) model.getAttribute(identifier[1])) {
                    body.append(contents);
                }
            } else if (identifier[0].equals("^if")) {
                if (!(boolean) model.getAttribute(identifier[1])) {
                    body.append(contents);
                }
            }
        }
    }

    private static String replaceAttribute(Model model, String contents) {
        for (String attributeName : model.getAttributeSet()) {
            Object object = model.getAttribute(attributeName);
            if (object instanceof String) {
                contents = contents.replace("{{" + attributeName + "}}", (String)object);
            }
        }
        return contents;
    }

    private static byte[] generateStaticBody(String view) throws IOException {
        return Files.readAllBytes(RequestUtil.findResource(view).toPath());
    }

    private static String collectContents(BufferedReader br) throws IOException {
        StringBuilder contents = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            if (line.trim().matches(TEMPLATE_END_REGEX)) {
                break;
            }
            contents.append(line);
        }

        return contents.toString();
    }
}
