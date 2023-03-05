package LESW.Together.domain.auth.controller;

import LESW.Together.domain.auth.dto.AuthenticationResponse;
import LESW.Together.domain.user.dto.SignupUserDTO;
import LESW.Together.domain.user.FieldErrorMessageOfSignupUser;
import LESW.Together.domain.user.dto.UserDto;
import LESW.Together.domain.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService userService;


    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Validated SignupUserDTO signUpUserDTO, BindingResult bindingResult) {
        log.info("회원 가입 컨트롤러 시작");
        if(bindingResult.hasErrors()) {
            log.info("검증 실패");
            FieldErrorMessageOfSignupUser fieldErrorMessageOfSignupUser = new FieldErrorMessageOfSignupUser();


            log.info("errors={}", bindingResult);
            log.info("messages={}", fieldErrorMessageOfSignupUser);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse());
        }


        AuthenticationResponse registerResponse = userService.save(signUpUserDTO);
        log.info("회원 가입 컨트롤러 종료");
        return ResponseEntity.status(HttpStatus.OK).body(registerResponse);
    }

    @PostMapping("/authentication")
    @ResponseBody
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) {
        log.info("로그인 컨트롤러 시작");
        AuthenticationResponse response = userService.userSignIn(userDto);
        log.info("로그인 컨트롤러 종료");

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
