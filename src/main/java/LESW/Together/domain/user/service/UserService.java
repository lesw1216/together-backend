package LESW.Together.domain.user.service;

import LESW.Together.domain.user.Question;
import LESW.Together.domain.user.dto.SignupUserDTO;
import LESW.Together.domain.user.User;
import LESW.Together.domain.user.dto.UserDto;
import LESW.Together.domain.user.repository.QuestionRepository;
import LESW.Together.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository, QuestionRepository questionRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public void save(SignupUserDTO signupUserDTO) {
        // signupUser.password encoding.
        // signupUserDTO에서 회원 정보를 하나 새로 생성한다.
        // userRepository에 회원을 저장한다.
        // 저장후 나온 유저의 index id로 질문 정보를 질문 테이블에 저장한다.

        String encodePwd = passwordEncoder.encode(signupUserDTO.getPassword());

        User saveUser = userRepository.createUser(new User(signupUserDTO.getUserId(), encodePwd, signupUserDTO.getUsername(), "user"));
        Question saveQuestion = questionRepository.createQuestion(new Question(saveUser.getId(), signupUserDTO.getKeyNum(), signupUserDTO.getKeyValue()));

        log.info("[회원가입 유저][{}][{}]", saveUser, saveQuestion);
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

    public Map<String, Object> userSignIn(UserDto userDto) {
        String signInUserPassword = userDto.getPassword();
        String signInUserId = userDto.getUserId();

        Optional<User> findUserByUserId = userRepository.findUserByUserId(signInUserId);
        User user = findUserByUserId.orElse(new User());

        HashMap<String, Object> signReturnMap = new HashMap<>();
        if (passwordEncoder.matches(signInUserPassword, user.getPassword())) {
            log.info("로그인 인증 성공 분기");
            // 로그인 성공
            UserDetails userDetails = userDetailsService.loadUserByUsername(signInUserId);
            // 로그인이 성공하면 Authentication 생성?
            signReturnMap.put("userDetails", userDetails);
        } else {
            log.info("실패 분기");
        }

        return signReturnMap;
    }

}
