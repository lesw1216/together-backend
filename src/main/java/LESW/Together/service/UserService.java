package LESW.Together.service;

import LESW.Together.domain.user.Question;
import LESW.Together.domain.user.SignupUserDTO;
import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.QuestionRepository;
import LESW.Together.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, QuestionRepository questionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(SignupUserDTO signupUserDTO) {
        // signupUser.password encoding.
        // signupUserDTO에서 회원 정보를 하나 새로 생성한다.
        // userRepository에 회원을 저장한다.
        // 저장후 나온 유저의 index id로 질문 정보를 질문 테이블에 저장한다.

        String encodePwd = passwordEncoder.encode(signupUserDTO.getPassword());
        User newUser = new User(signupUserDTO.getUserId(), encodePwd, signupUserDTO.getUsername(), "user");
        User saveUser = userRepository.createUser(newUser);

        Question userQuestion = new Question(saveUser.getId(), signupUserDTO.getKeyNum(), signupUserDTO.getKeyValue());
        Question saveQuestion = questionRepository.createQuestion(userQuestion);

        log.info("[회원가입 유저][{}][{}]=", saveUser, saveQuestion);
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.readUser(id);
        return user.get();
    }

    public void remove(Long id) {
        userRepository.deleteUser(id);
    }

    public void update(Long id, User updateUser) {
        userRepository.updateUser(id, updateUser);
    }

}
