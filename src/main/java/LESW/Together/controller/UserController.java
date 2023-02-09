package LESW.Together.controller;

import LESW.Together.domain.user.User;
import LESW.Together.domain.user.UserDto;
import LESW.Together.service.UserService;
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
    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }



    @PostMapping("/api/users")
    public void UserSignUp(@RequestBody UserDto userDto) {
        String hashPassword = passwordEncoder.encode(userDto.getPassword());
        User saveUser = userService.save(new User(userDto.getUserId(), hashPassword, userDto.getUsername(), null));
        log.info("회원가입[{}]", saveUser);
    }
}
