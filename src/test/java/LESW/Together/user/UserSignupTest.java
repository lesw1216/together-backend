package LESW.Together.user;

import LESW.Together.domain.user.Question;
import LESW.Together.domain.user.Role;
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
    public UserJdbcTemplateRepository userRepository;

    @Autowired
    public QuestionRepository questionRepository;

    @Test
    void UserCreate() {
        User user = new User("testUser","leofsdf", "홍길동","esfds", Role.USER);
        User createUser = userRepository.createUser(user);
        Assertions.assertThat(user).isEqualTo(createUser);
    }

    @Test
    void QuestionCreate() {
        User user = new User("testUser", "1234", "홍길동", "user", Role.USER);
        User saveUser = userRepository.createUser(user);
        Question saveQuestion = new Question(saveUser.getId(), 0, "상정고");

        Question question = questionRepository.createQuestion(saveQuestion);

        Assertions.assertThat(saveQuestion).isEqualTo(question);
    }

    @Test
    void UserFInd() {
        User newUser = new User("testUser", "1234", "홍길동", "user", Role.USER);
        User saveUser = userRepository.createUser(newUser);

        Optional<User> findUserById = userRepository.readUser(saveUser.getId());
        User findUser = findUserById.get();

        Assertions.assertThat(findUser.getId()).isEqualTo(saveUser.getId());
        /*
        * 1. 새로운 유저를 생성한다.
        * 2. 레포지토리의 유저 저장 메서드에 매개변수 유저를 담고 호출한다.
        * 3. 저장이 성공되고 반환된 유저의 인덱스 아이디를 리포지토리의 유저 조회 메서드의 파라미터로 넘겨준다.
        * 4. 있다면 optional로 싸여 나오게 된다.
        * */
    }



    @Test
    void UserDelete() {
        User newUser = new User("testUser", "1234", "홍길동", "user", Role.USER);
        User saveUser = userRepository.createUser(newUser);
        userRepository.deleteUser(saveUser.getId());

    }
}
