package com.hallym.festival.global.security.filter;


import com.google.gson.Gson;
import com.hallym.festival.global.security.exception.RefreshTokenException;
import com.hallym.festival.global.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshPath; //토큰 갱신에 사용할 경로

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (!path.equals(refreshPath)) {
            log.info("skip refresh token filter.....");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("....토큰 갱신을 위해 .......Refresh Token Filter가 실행 중입니다...............1");

        //전송된 JSON에서 accessToken과 refreshToken을 얻어온다.
        Map<String, String> tokens = parseRequestJSON(request);

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        log.info("accessToken: " + accessToken);
        log.info("refreshToken: " + refreshToken);

        try{ //예외 발생 시 메세지 전송하고 메소드 실행 종료
            checkAccessToken(accessToken);
        }catch(RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
        }

        Map<String, Object> refreshClaims = null;

        try {
            refreshClaims = checkRefreshToken(refreshToken); //checkRefreshToken 처리
            log.info(refreshClaims);

        }catch(RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
            return;
        }

        //Refresh Token의 유효시간이 얼마 남지 않은 경우
        Integer exp = (Integer)refreshClaims.get("exp");

        Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);

        Date current = new Date(System.currentTimeMillis());

        //만료 시간과 현재 시간의 간격 계산
        //만일 3일 미만인 경우에는 Refresh Token도 다시 생성
        long gapTime = (expTime.getTime() - current.getTime());

        log.info("-----------------------------------------");
        log.info("current: " + current);
        log.info("expTime: " + expTime);
        log.info("gap: " + gapTime );

        String mid = (String)refreshClaims.get("mid");

        //이상태까지 오면 무조건 AccessToken은 새로 생성
        String accessTokenValue = jwtUtil.generateToken(Map.of("mid", mid), 1);

        String refreshTokenValue = tokens.get("refreshToken");

        //RefrshToken이 3일도 안남았다면..
        if(gapTime < (1000 * 60  * 3  ) ){
        //if(gapTime < (1000 * 60 * 60 * 24 * 3  ) ){
            log.info("new Refresh Token required...  ");
            refreshTokenValue = jwtUtil.generateToken(Map.of("mid", mid), 30);
        }

        log.info("Refresh Token result....................");
        log.info("accessToken: " + accessTokenValue);
        log.info("refreshToken: " + refreshTokenValue);

        sendTokens(accessTokenValue, refreshTokenValue, response);


    }

    private Map<String,String> parseRequestJSON(HttpServletRequest request) {

        //JSON 데이터를 분석해서 mid, mpw 전달 값을 Map으로 처리
        try(Reader reader = new InputStreamReader(request.getInputStream())){

            Gson gson = new Gson();

            return gson.fromJson(reader, Map.class);

        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    private void checkAccessToken(String accessToken)throws RefreshTokenException { //AccessToken 검증 메소드

        try{
            jwtUtil.validateToken(accessToken);
        }catch (ExpiredJwtException expiredJwtException){
            log.info("Access Token has expired"); //accessToken이 만료된 경우에는 로그만 출력
        }catch(Exception exception){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS); //문제가 생기면 예외 전달
        }
    }

    private Map<String, Object> checkRefreshToken(String refreshToken)throws RefreshTokenException{ //RefreshToken 검증 메소드

        try {
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);

            return values; //정상이라면 내용물을 Map으로 반환

        }catch(ExpiredJwtException expiredJwtException){ //문제 발생 시 RefreshTokenException 전달
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        }catch(Exception exception){
            exception.printStackTrace();
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }

    private void sendTokens(String accessTokenValue, String refreshTokenValue, HttpServletResponse response) { //최종적으로 토큰 전송


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(Map.of("accessToken", accessTokenValue,
                "refreshToken", refreshTokenValue));

        try {
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
















