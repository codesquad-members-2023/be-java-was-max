package cafe.app.user.repository;

import cafe.app.user.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MemoryUserRepository implements UserRepository {

    private static final List<User> store = new ArrayList<>();
    private static long sequence = 0;

    static {
        // 샘플 유저 데이터 초기화
        IntStream.range(0, 100)
                .mapToObj(i -> User.builder()
                        .id(++sequence)
                        .userId("user" + i)
                        .password("useruser" + i)
                        .name("김용환" + i)
                        .email("user" + i + "@naver.com")
                        .build())
                .forEach(store::add);
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public Optional<User> findById(Long id) {
        return store.stream().filter(user -> user.getId().equals(id)).findAny();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream().filter(user -> user.getUserId().equals(userId)).findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

    @Override
    public User save(User user) {
        User newUser = User.builder()
                .id(nextId())
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        store.add(newUser);
        return newUser;
    }

    @Override
    public User modify(User user) {
        User originalUser = findById(user.getId()).orElseThrow();
        originalUser.modify(user);
        return originalUser;
    }

    @Override
    public synchronized int deleteAll() {
        int deleteUserCount = store.size();
        store.clear();
        sequence = 0;
        return deleteUserCount;
    }

    private synchronized Long nextId() {
        return ++sequence;
    }
}
