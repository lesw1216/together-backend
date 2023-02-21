package LESW.Together.user;

import LESW.Together.domain.user.Question;
import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.QuestionRepository;
import LESW.Together.domain.user.repository.jdbcTemplate.UserJdbcTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
public class UserSignupTest {


    @Autowired
    public UserJdbcTemplateRepository repository;

    @Autowired
    public QuestionRepository questionRepository;

    @Test
    void UserCreate() {
        User user = new User("testUser","leofsdf", "홍길동","esfds");
        User createUser = repository.createUser(user);
        Assertions.assertThat(user).isEqualTo(createUser);
    }

    @Test
    void QuestionCreate() {
        User user = new User("testUser", "1234", "홍길동", "user");
        User saveUser = repository.createUser(user);
        Question saveQuestion = new Question(saveUser.getId(), 0, "상정고");

        Question question = questionRepository.createQuestion(saveQuestion);

        Assertions.assertThat(saveQuestion).isEqualTo(question);
    }

    @Test
    void UserFInd() {
        User user = new User("testUser", "1234", "홍길동", "user");
        User saveUser = repository.createUser(user);
        Long id = saveUser.getId();
        log.info(String.valueOf(id));


        Optional<User> findUserById = repository.readUser(id);
        User findUser = findUserById.get();
        Assertions.assertThat(saveUser.getId()).isEqualTo(findUser.getId());
    }

    @Test
    void UserDelete() {
        User newUser = new User("testUser", "1234", "홍길동", "user");
        User saveUser = repository.createUser(newUser);
        repository.deleteUser(saveUser.getId());

    }
}
