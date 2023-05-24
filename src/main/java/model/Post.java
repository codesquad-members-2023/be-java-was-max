package model;

import java.util.concurrent.atomic.AtomicLong;

public class Post {

    private static final AtomicLong idCreator = new AtomicLong();

    private long id;
    private String title;
    private String nickname;
    private String content;

    public Post(String title, String nickname, String content) {
        this.id = idCreator.incrementAndGet();
        this.title = title;
        this.nickname = nickname;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }
}
