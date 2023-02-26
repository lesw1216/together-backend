package LESW.Together.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// 토큰을 생성하고 검증하는 클래스
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거친다.
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {


    // 토큰 유효시간
    private final long TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;


    /**
     * Jwt Token Validation, if token's extractName is not equals username of UserDetails Instance or Expired, return false.
     * The opposite is true.
     * @param token
     * @param userDetails
     * @return boolean
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Check the JwtToken's Expire, if JwtToken is Expired, return false.
     * if no Expired, return true.
     * @param token
     * @return true or false
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(SecretKeyGenerator.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }

    /**
     * Get username of JwsClaim.
     * @param token
     * @return type of username, String.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * this Method generates Jwt Token that only one use Claim that UserDetail.
     * @param userDetails
     * @return return JwtToken.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * this Method generates Jwt Token that use multiple Claims and UserDetail is default Claim.
     * @param extraClaims
     * @param userDetails
     * @return return JwtToken.
     */
    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
                .signWith(SecretKeyGenerator.getSigningKey())
                .compact();
    }

    /**
     * When want to get specific Claim, use Function<>
     *
     * @param token
     * @param claimsResolver
     * @return specific Claim type
     * @param <T>
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * return to get All Claims.
     * @param token
     * @return Claims
     */
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SecretKeyGenerator.getSigningKey())
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new JwtException("JWT 토큰 인증 오류");
        }

    }

    /**
     * Jwt Token make signatureKey by signKey, and verify Request header's Jwt Token by SignKey.
     * this method is able to get signKey for Jwt Token make or verify.
     * @return HMAC-SHA algorithms based on SecretKey.
     */

}
