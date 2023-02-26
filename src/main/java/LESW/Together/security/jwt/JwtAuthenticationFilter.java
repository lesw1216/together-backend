package LESW.Together.security.jwt;

import LESW.Together.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtFilter 시작");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userId;


        // Authorization 헤더가 없거나, 첫 문자열이 Bearer이 아닌 경우 다음 filter로 이동
        // 로그인, 또는 회원가입인 경우
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("if (authHeader == null || !authHeader.startsWith(\"Bearer \")) 시작");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰의 유효성 검증.
        jwt = authHeader.substring(7);
        userId = jwtTokenProvider.extractUsername(jwt);
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) : true");
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
            if (jwtTokenProvider.isTokenValid(jwt, userDetails)) {
                log.info("if(jwtTokenProvider.isTokenValid(jwt, userDetails) 시작");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("if(jwtTokenProvider.isTokenValid(jwt, userDetails) 종료");
            }
            log.info("if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) : 종료");
        }

        filterChain.doFilter(request, response);
    }
}
