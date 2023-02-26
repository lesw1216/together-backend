package LESW.Together.domain.auth.service;

import LESW.Together.domain.auth.dto.AuthenticationResponse;
import LESW.Together.domain.user.Question;
import LESW.Together.domain.user.dto.SignupUserDTO;
import LESW.Together.domain.user.User;
import LESW.Together.domain.user.dto.UserDto;
import LESW.Together.domain.user.repository.QuestionRepository;
import LESW.Together.domain.user.repository.UserRepository;
import LESW.Together.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse save(SignupUserDTO signupUserDTO) {
        log.info("save Service 시작");
        // signupUser.password encoding.
        // signupUserDTO에서 회원 정보를 하나 새로 생성한다.
        // userRepository에 회원을 저장한다.
        // 저장후 나온 유저의 index id로 질문 정보를 질문 테이블에 저장한다.

        User user = User.builder()
                .userId(signupUserDTO.getUserId())
                .password(passwordEncoder.encode(signupUserDTO.getPassword()))
                .userName(signupUserDTO.getUsername())
                .userRole("USER")
                .build();

        User saveUser = userRepository.createUser(user);

        Question question = Question.builder()
                .id(saveUser.getId())
                .keyNum(signupUserDTO.getKeyNum())
                .keyValue(signupUserDTO.getKeyValue())
                .build();

        Question saveQuestion = questionRepository.createQuestion(question);
        log.info("[회원가입 유저][{}][{}]", saveUser, saveQuestion);

        String jwt = jwtTokenProvider.generateToken(user);
        log.info("save Service 종료");

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();

    }

    public User findById(Long id) {
        Optional<User> user = userRepository.readUser(id);
        return user.get();
    }

    public void remove(Long id) {
        userRepository.deleteUser(id);
    }

    public SignupUserDTO update(Long id, SignupUserDTO updateUser, String role) {
//        new User(updateUser.getUserId(), updateUser.getPassword(), updateUser.getUsername(), role);

        userRepository.updateUser(id, new User());
        return updateUser;
    }

    public AuthenticationResponse userSignIn(UserDto userDto) {
        log.info("로그인 서비스 시작");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getUserId(),
                userDto.getPassword()
                )
        );
        log.info("로그인 서비스에서 authenticationManager 인증 완료");

        User userByUserId = userRepository.findUserByUserId(userDto.getUserId()).orElseThrow();
        String jwt = jwtTokenProvider.generateToken(userByUserId);
        log.info("로그인 서비스 종료");

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

}
