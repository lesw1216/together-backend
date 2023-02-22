package LESW.Together.controller;

import LESW.Together.domain.user.SignupUserDTO;
import LESW.Together.domain.user.FieldErrorMessageOfSignupUser;
import LESW.Together.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Autowired

    public UserController(UserService userService) {
        this.userService = userService;
    }




    @PostMapping("/api/users")
    @ResponseBody
    public Object UserSignUp(@RequestBody @Validated SignupUserDTO signUpUserDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.info("검증 실패");
            FieldErrorMessageOfSignupUser fieldErrorMessageOfSignupUser = new FieldErrorMessageOfSignupUser();


            log.info("errors={}", bindingResult);
            log.info("messages={}", fieldErrorMessageOfSignupUser);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult);
        }


        userService.save(signUpUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body("msg:{ 가입 성공 }");
    }
}
