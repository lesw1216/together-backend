package LESW.Together.service;

import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        user.setRole("user");
        return repository.createUser(user);
    }

    public User findById(Long id) {
        Optional<User> user = repository.readUser(id);
        return user.get();
    }

    public void remove(Long id) {
        repository.deleteUser(id);
    }

    public void update(Long id, User updateUser) {
        repository.updateUser(id, updateUser);
    }

}
