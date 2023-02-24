package LESW.Together.service;

import LESW.Together.domain.user.dto.SignupUserDTO;
import LESW.Together.domain.user.User;
import LESW.Together.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    public JdbcTemplate template;

    @BeforeEach
    void AutoIncrementReset() {
        template.update("ALTER TABLE users AUTO_INCREMENT = 1");
    }

    @Test
    @DisplayName("서비스 로직에서 유저 저장하기")
    void save() {
        userService.save(new SignupUserDTO("test","1234", "홍길동", 0, "답변"));
    }

    @Test
    @DisplayName("서비스 로직에서 유저 인덱스 아이디로 유저 찾기")
    void findById() {
        userService.save(new SignupUserDTO("test","1234", "홍길동", 0, "답변"));
        User findUserById = userService.findById(1L);
        Assertions.assertThat(1L).isEqualTo(findUserById.getId());
    }

    @Test
    @DisplayName("서비스 로직에서 유저 인덱스 아이디로 유저 삭제하기")
    void remove() {
        userService.save(new SignupUserDTO("test","1234", "홍길동", 0, "답변"));
        userService.remove(1L);

        Assertions.assertThatThrownBy(() -> {
            userService.findById(1L);
        }).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("유저 정보 업데이트")
    void update() {
        /*
        * 새로운 유저 생성
        * 저장
        * 수정한 유저 생성
        * upate 호출
        * */
        userService.save(new SignupUserDTO("test","1234", "홍길동", 0, "답변"));
        SignupUserDTO updateUser = new SignupUserDTO("test", "323", "리상우", 2, "랑우");
        SignupUserDTO updatedUser = userService.update(1L, updateUser, "user");
        Assertions.assertThat(updateUser).isEqualTo(updatedUser);
    }
}