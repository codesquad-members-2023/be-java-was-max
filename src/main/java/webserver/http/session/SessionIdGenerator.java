package webserver.http.session;

import java.util.Random;
import java.util.stream.Collectors;

public class SessionIdGenerator {

    private static final int SIZE = 16;

    private final Random random;

    public SessionIdGenerator(Random random) {
        this.random = random;
    }

    public String generateSessionId() {
        return random.ints(SIZE, 0, 9)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }
}
