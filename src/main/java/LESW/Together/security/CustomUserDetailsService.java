package LESW.Together.security;

import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements  UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("customUserDetailsService invoke loadUserByUsername");
        log.info("customUserDetailsService argument={}", userId);
        Optional<User> userByUserId = userRepository.findUserByUserId(userId);
        if (userByUserId.isEmpty()) {
            throw new UsernameNotFoundException("not found userId = " + userId);
        }
        User findUser = userByUserId.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(findUser.getUserName())
                .password(findUser.getPassword())
                .authorities(findUser.getUserRole())
                .build();
    }
}
