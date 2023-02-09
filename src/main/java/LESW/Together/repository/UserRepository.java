package LESW.Together.repository;

import LESW.Together.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User createUser(User user);

    Optional<User> readUser(Long id);

    List<User> readAllUser();

    void updateUser(Long id, User updateUser);

    User deleteUser(Long id);
}
