package LESW.Together.user;

import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.jdbcTemplate.UserJdbcTemplateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSignupTest {


    @Autowired
    public UserJdbcTemplateRepository repository;

    @Test
    void UserCreate() {
        User user = new User("userA", "pass", "홍길동", "user");
        User createUser = repository.createUser(user);

        Assertions.assertThat(user).isEqualTo(createUser);
    }
}
