package view;

import http.ContentType;

import java.nio.file.Paths;

public class ViewResolver {
    private final String absolutePath = Paths.get("").toAbsolutePath().toString();
    private final String classPath = absolutePath + "/src/main/resources";
    private final String staticPath = classPath + "/static";
    private final String templatesPath = classPath + "/templates";
    private String contentType = null;
    private String totalPath = null;


    public String run(String viewPath){
        this.contentType = ContentType.get(viewPath);
        // TODO: static 먼저 탐방하고, 없으면 template에서 찾고, 없으면 에러를 발생하게끔 수정하기
        //  해당 file 부터 찾게, 알맞은 클래스를 찾아서 위치 수정하기
        findTotalPath(viewPath);
    }
    private void findTotalPath(String viewPath){
        // TODO: static 폴더 부터 찾고, 없으면 templates 폴더 에서 찾기.
        // 둘 다 없으면 에러 발생
        staticPath + viewPath;
        templatesPath + viewPath;

    }
    getContentType()
    getTotalPath()
}
