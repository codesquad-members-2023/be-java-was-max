package servlet.domain;

import servlet.domain.request.target.OriginForm;

import java.util.Map;

import static servlet.domain.HttpRequestMethod.GET;
import static servlet.domain.HttpRequestMethod.from;

public class StartLine {
    private static final String LINE_DELIMITER = " ";
    public static final int HTTP_METHOD_INDEX = 0;
    public static final int REQUEST_INDEX = 1;
    public static final int HTTP_VERSION_INDEX = 2;
    private final HttpRequestMethod httpRequestMethod;
    private final OriginForm originForm;
    private final HttpVersion httpVersion;

    private StartLine(HttpRequestMethod httpRequestMethod, OriginForm originForm, HttpVersion httpVersion) {
        this.httpRequestMethod = httpRequestMethod;
        this.originForm = originForm;
        this.httpVersion = httpVersion;
    }

    public static StartLine parse(String startLineString) {
        String[] startLineInfo = startLineString.split(LINE_DELIMITER);
        HttpRequestMethod httpMethodInfo = from(startLineInfo[HTTP_METHOD_INDEX]);
        OriginForm originForm = OriginForm.from(startLineInfo[REQUEST_INDEX]);
        HttpVersion httpVersion = HttpVersion.from(startLineInfo[HTTP_VERSION_INDEX]);

        return new StartLine(httpMethodInfo, originForm, httpVersion);
    }

    public String getUrl() {
        return originForm.getPathValue();
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public Map<String, String> getParameters() {
        return originForm.getParameters();
    }

    public boolean isSamePath(String path) {
        return originForm.isSamePath(path);
    }

    public boolean isSameParameterCount(int parameterCount) {
        return originForm.isSameParameterCount(parameterCount);
    }

    public boolean hasParameter() {
        return originForm.hasParameter();
    }

    public boolean isSameMethod(String httpRequestMethod) {
        return this.httpRequestMethod.isSameName(httpRequestMethod);
    }

    public boolean hasBody() {
        return this.httpRequestMethod != GET;
    }

    public boolean startsWith(String path) {
        return originForm.startsWith(path);
    }
}
