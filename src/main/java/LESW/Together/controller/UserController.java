package LESW.Together.controller;

import LESW.Together.domain.user.User;
import LESW.Together.domain.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @PostMapping("/api/users")
    public void UserSignUp(@RequestBody UserDto userDto) {
        User user = new User(userDto.getUserId(), userDto.getPassword(), userDto.getUsername());
        log.info("user={}", user);
    }
}
