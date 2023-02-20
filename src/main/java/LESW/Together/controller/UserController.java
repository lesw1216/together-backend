package LESW.Together.controller;

import LESW.Together.domain.user.SignupUserDTO;
import LESW.Together.domain.user.User;
import LESW.Together.domain.user.FieldErrorMessageOfSignupUser;
import LESW.Together.service.UserService;
import LESW.Together.validator.ValidatorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private final ValidatorMessage validatorMessage;
    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService, ValidatorMessage validatorMessage) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.validatorMessage = validatorMessage;
    }



    @PostMapping("/api/users")
    @ResponseBody
    public Object UserSignUp(@RequestBody @Validated SignupUserDTO signUpUserDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.info("검증 실패");
            FieldErrorMessageOfSignupUser fieldErrorMessageOfSignupUser = new FieldErrorMessageOfSignupUser();


            log.info("errors={}", bindingResult);
            log.info("messages={}", fieldErrorMessageOfSignupUser);
            return fieldErrorMessageOfSignupUser;
        }


        String hashPassword = passwordEncoder.encode(signUpUserDTO.getPassword());
        User saveUser = userService.save(new User(signUpUserDTO.getUserId(), hashPassword, signUpUserDTO.getUsername(), null));
        log.info("회원가입[{}]", saveUser);
        return saveUser;
    }
}
