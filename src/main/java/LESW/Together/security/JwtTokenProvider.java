package LESW.Together.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

// 토큰을 생성하고 검증하는 클래스
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거친다.
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "myprojectsecret";

    // 토큰 유효시간
    private long tokenValidTime = 30 * 60 * 1000L;

    private final UserDetailsService userDetailsService;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // 객체를 초기화?
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);// JWT payload에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 유효기간 설정
                .signWith(key)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        userDetailsService.loadUserByUsername(this.getUserPk(token));
        return null;
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
//        return Jwts.parserBuilder().setSigningKey(key)
        return null;
    }

}
