package LESW.Together.controller;

import LESW.Together.domain.user.User;
import LESW.Together.domain.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    @PostMapping("/api/users")
    public void UserSignUp(@RequestBody UserDto userDto) {
        String hashPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto.getUserId(), hashPassword, userDto.getUsername());
        log.info("user={}", user);
    }
}
