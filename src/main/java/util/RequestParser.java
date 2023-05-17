package util;

import model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    public Request readNParseRequest(InputStream in) {
        Request request = new Request();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Map<String, String> requestLine = readNParseRequestLine(br);
            Map<String, String> header = readNParseHeader(br);
//            String body = readBody(br, header);
            request.addRequestLine(requestLine);
            request.addHeader(header);
//            request.setBody(body);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return request;
    }

    private Map<String, String> readNParseRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine().trim();
        logger.debug("Request Line : {}", requestLine);
        Map<String,String> parsedRequestLine = parseStartLine(requestLine);

        return parsedRequestLine;
    }

    private Map<String, String> readNParseHeader(BufferedReader br) throws IOException {
        String header;
        Map<String, String> parsedHeader = new HashMap<>();

        while (!(header = br.readLine().trim()).equals("")) {
            Map<String, String> temp = parseHeader((header));
            parsedHeader.putAll(temp);
        }
        return parsedHeader;
    }

    private String readBody(BufferedReader br, Map<String, String> header) throws IOException {
        int input;
        StringBuffer sb = new StringBuffer();

        while ((input = br.read()) > 0) {
            sb.append((char) input);
        }
        return sb.toString();
    }


    public static Map<String, String> parseStartLine (String startLine) {
        Map<String, String> container = new HashMap<>();
        String[] splitLine = startLine.split(" ");

        container.put("Method", splitLine[0]);
        container.put("URL", splitLine[1]);
        container.put("Protocol", splitLine[2]);
        return container;
    }

    public static Map<String, String> parseHeader (String header) {
        Map<String, String> container = new HashMap<>();
        String[] splitLine = header.split(":");
        List<String> trimmed= Arrays.stream(splitLine).map(s -> s.trim()).collect(Collectors.toList());

        container.put(trimmed.get(0), trimmed.get(1));
        return container;
    }

    public static Map<String, String> parseURL(String URL) throws UnsupportedEncodingException {
        Map<String, String> dto = new HashMap<>();
        String[] URLSplit = URL.split("\\?", 2);
        String[] form = URLSplit[1].split("&");

        for (String element : form) {
            String[] field = element.split("=");
            dto.put(field[0], URLDecoder.decode(field[1], "UTF-8"));
        }
        return dto;
    }

    public static Map<String, String> parseBody(String body) throws UnsupportedEncodingException {
        Map<String, String> dto = new HashMap<>();
        String decodedBody = URLDecoder.decode(body, "UTF-8");
        String[] form = decodedBody.split("&");

        for (String element : form) {
            String[] field = element.split("=");
            dto.put(field[0], field[1]);
        }
        return dto;
    }
}
