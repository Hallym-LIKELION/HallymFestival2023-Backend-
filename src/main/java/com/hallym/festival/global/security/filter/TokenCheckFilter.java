package com.hallym.festival.global.security.filter;

import com.hallym.festival.global.security.APIUserDetailsService;
import com.hallym.festival.global.security.exception.AccessTokenException;
import com.hallym.festival.global.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Log4j2
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter { //토큰을 검사하는 클래스

    private final APIUserDetailsService apiUserDetailsService; //mid 값을 가져오기 위해 의존성 주입
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();


        if (!path.contains("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("..........Token Check Filter가 JWT 토큰을 검사합니다..........................");
        log.info("JWTUtil: " + jwtUtil);


        try{

            Map<String, Object> payload = validateAccessToken(request);

            //mid값을 이용해서
            String mid = (String)payload.get("mid");

            log.info("mid: " + mid);

            //user 상세 정보를 구하고
            UserDetails userDetails = apiUserDetailsService.loadUserByUsername(mid);

            //UsernamePasswordAuthenticationToken 객체를 구성한다.
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

            //SecurityContextHolder 객체로 인증정보를 저장하면 JWT기반 작업에서도 @PreAuthorize를 사용할 수 있음
            SecurityContextHolder.getContext().setAuthentication(authentication); // 시큐리티에서 사용할 수 있게 하는 중요한 부분 :)

            validateAccessToken(request);
            filterChain.doFilter(request,response);
        }catch(AccessTokenException accessTokenException){
            accessTokenException.sendResponseError(response);
        }
    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException { //토큰 검증 메소드

        String headerStr = request.getHeader("Authorization");

        if(headerStr == null  || headerStr.length() < 8){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        //Bearer 생략
        String tokenType = headerStr.substring(0,6);
        String tokenStr =  headerStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try{
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            //validateToken()을 실행해서 문제가 발생하면 AccessTokenException 발생

            return values;
        }catch(MalformedJwtException malformedJwtException){
            log.error("MalformedJwtException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        }catch(SignatureException signatureException){
            log.error("SignatureException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        }catch(ExpiredJwtException expiredJwtException){
            log.error("ExpiredJwtException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }

}
