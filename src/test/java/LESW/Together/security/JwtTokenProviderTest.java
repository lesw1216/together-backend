package LESW.Together.security;

import LESW.Together.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void createJwtToken() {
    }

//    @Test
//    void readJwtToken() {
//        String jwtToken = jwtTokenProvider.createToken("1");
//        Jws<Claims> claimsJws = jwtTokenProvider.readToken(jwtToken);
//        log.info("Get Header=[{}]", claimsJws.getHeader());
//        log.info("Get Body=[{}]", claimsJws.getBody());
//        log.info("Get Signature=[{}]", claimsJws.getSignature());
//        log.info("Create JwtToken=[{}]", jwtToken);
//    }

}