package db;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import model.Post;

public class PostDatabase {

    private static Map<Long, Post> posts = Maps.newHashMap();

    public static void addPost(Post post) {
        posts.put(post.getId(), post);
    }

    public static Post findPostById(long id) {
        return posts.get(id);
    }

    public static Collection<Post> findAll() {
        return posts.values();
    }
}
