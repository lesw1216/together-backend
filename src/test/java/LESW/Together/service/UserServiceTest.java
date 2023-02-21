package LESW.Together.service;

import LESW.Together.domain.user.SignupUserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void remove() {
    }

    @Test
    void update() {
    }
}