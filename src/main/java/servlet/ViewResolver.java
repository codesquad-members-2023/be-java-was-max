package servlet;

import db.PostDatabase;
import db.SessionDB;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import model.Post;
import model.User;
import servlet.domain.HttpResponse;
import servlet.domain.HttpResponseStatus;

public class ViewResolver {

    private static final String SRC_MAIN_RESOURCES = "src/main/resources";
    private static final String PREFIX = "/";
    private static final String indexAdder = "                <li>\n"
            + "                    <div class=\"wrap\">\n"
            + "                        <div class=\"main\">\n"
            + "                            <strong class=\"subject\">\n"
            + "                                <a href=\"./qna/show.html\">title</a>\n"
            + "                            </strong>\n"
            + "                            <div class=\"auth-info\">\n"
            + "                                <i class=\"icon-add-comment\"></i>\n"
            + "                                <span class=\"time\">2016-01-15 18:47</span>\n"
            + "                                <a href=\"./user/profile.html\" class=\"author\">nickname</a>\n"
            + "                            </div>\n"
            + "                            <div class=\"reply\" title=\"댓글\">\n"
            + "                                <i class=\"icon-reply\"></i>\n"
            + "                                <span class=\"point\">8</span>\n"
            + "                            </div>\n"
            + "                        </div>\n"
            + "                    </div>\n"
            + "                </li>\n";

    private ViewResolver() {
    }

    public static HttpResponse resolve(String result) throws IOException {
        String body;
        String session = null;
        if (result.contains("sid=")) {
            int index = result.indexOf("sid=");
            session = result.substring(index);
            result = result.substring(0, index - 1);
        }

        int postId = -1;
        if (result.startsWith("/templates/qna/show.html")){
            int index = result.lastIndexOf("?");
            String pathInfo = result.substring(index);
            String[] split = pathInfo.split("=");
            postId = Integer.parseInt(split[1]);
            result = result.substring(0, index);

        }

        if (result.startsWith(PREFIX)) {
            Path path = Paths.get(SRC_MAIN_RESOURCES + result);
            body = Files.readString(path);
        } else {
            body = result;
        }
        if (result.equals("/templates/index.html")) {
            body = resolveIndexPage(body);
        }
        if (postId != -1){
            body = resolveShowPage(postId,body);
        }

        return session == null ? new HttpResponse(HttpResponseStatus.OK, body) :
                new HttpResponse(HttpResponseStatus.OK, loginSession(body, session), session);
    }

    private static String resolveShowPage(int postId, String body) {
        Post post = PostDatabase.findPostById(postId);
        body = body.replace("replaceTitle", post.getTitle());
        body = body.replace("replaceNickname", post.getNickname());
        body = body.replace("replaceContent", post.getContent());
        return body;
    }

    private static String resolveIndexPage(String body) {
        StringBuilder stringBuilder = new StringBuilder();

        Collection<Post> all = PostDatabase.findAll();
        for (Post post : all) {
            String now = indexAdder;
            now = now.replace("title", post.getTitle());
            now = now.replace("nickname", post.getNickname());
            now = now.replaceAll("href=\"./qna/show.html\"", "href=\"/posts/" + post.getId() + "\"");
            stringBuilder.append(now);
        }
        return body.replace("<!--replace-->", stringBuilder);
    }

    private static String loginSession(String body, String session) {
        User user = SessionDB.get(session);
        return body.replace("로그인", user.getName());
    }
}
