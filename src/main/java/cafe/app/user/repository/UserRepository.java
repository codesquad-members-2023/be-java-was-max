package cafe.app.user.repository;

import cafe.app.user.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    User save(User user);

    User modify(User user);

    int deleteAll();
}
