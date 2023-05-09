package webserver;

import annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        InetAddress inetAddress = connection.getInetAddress();
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", inetAddress,
                connection.getPort());

        try (InputStream in = connection.getInputStream()) {
            HttpRequest httpRequest = HttpRequest.getHttpRequest(in);
            MappingInfo mappingInfo = HandlerMapping(httpRequest);
            String result = HandlerAdapter(mappingInfo);
            HttpResponse httpResponse = viewResolver(result);
            response(httpResponse);
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            logger.error(e.getMessage());
        }
    }

    private void response(HttpResponse httpResponse) {
        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            httpResponse.written(out);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse viewResolver(String result) throws IOException {
        byte[] body;
        if (result.startsWith("/static")) {
            Path path = Paths.get("src/main/resources/" + result);
            body = Files.readAllBytes(path);
        } else if (result.startsWith("/")) {
            Path path = Paths.get("src/main/resources/templates" + result);
            body = Files.readAllBytes(path);
        } else {
            body = result.getBytes();
        }
        return new HttpResponse(HttpMethod.OK, body);
    }

    private String HandlerAdapter(MappingInfo mappingInfo)
            throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method method = mappingInfo.getMethod();
        Object object = mappingInfo.getObject();
        if (mappingInfo.getUrl().startsWith("/static")) {
            return mappingInfo.getUrl();
        }
        return (String) method.invoke(object);
    }

    private MappingInfo HandlerMapping(HttpRequest httpRequest) throws InvocationTargetException, IllegalAccessException,
            IOException, NoSuchMethodException {
        MainController mainController = MainController.getInInstance();
        String url = httpRequest.getUrl();

        if (url.startsWith("/static")) {
            return new MappingInfo(url);
        }

        Method[] declaredMethods = mainController.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                String value = annotation.value();
                if (value.equals(url)) {
                    return new MappingInfo(url, method, mainController);
                }
            }
        }
        throw new NoSuchMethodException();
    }
}
