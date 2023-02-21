package LESW.Together.domain.user.repository.jpa;

import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

//@Repository
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

//    @Autowired
    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> readUser(Long id) {
        User findUser = em.find(User.class, id);
        return Optional.ofNullable(findUser);
    }

    @Override
    public List<User> readAllUser() {
        return null;
    }

    @Override
    public void updateUser(Long id, User updateUser) {
        User findUser = em.find(User.class, id);
        findUser.setUserId(updateUser.getUserId());
        findUser.setPassword(updateUser.getPassword());
        findUser.setUserName(updateUser.getUserName());
    }

    @Override
    public void deleteUser(Long id) {
    }
}
