package LESW.Together.service;

import LESW.Together.domain.user.SignupUserDTO;
import LESW.Together.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void save() {
        userService.save(new SignupUserDTO("test","1234", "홍길동", 0, "답변"));
    }

    @Test
    void findById() {
        userService.save(new SignupUserDTO("test","1234", "홍길동", 0, "답변"));
        User findUserById = userService.findById(1L);
        Assertions.assertThat(1L).isEqualTo(findUserById.getId());
    }

    @Test
    void remove() {
    }

    @Test
    void update() {
    }
}