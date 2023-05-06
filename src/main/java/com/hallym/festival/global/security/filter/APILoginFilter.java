package com.hallym.festival.global.security.filter;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

@Log4j2
public class APILoginFilter extends AbstractAuthenticationProcessingFilter { //인증단계를 처리하고, 토큰 반환하는 클래스
    //POST 방식으로 호출해야함
    public APILoginFilter(String defaultFilterProcessesUrl) {//생성자
        super(defaultFilterProcessesUrl);
    }

    @Override //추상 메소드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("-------------- API Login 필터 실행 중 ---------------");

        if(request.getMethod().equalsIgnoreCase("GET")){
            log.info("GET METHOD NOT SUPPORT");
            return null;
        }

        log.info("-----------------------------------------");
        log.info(request.getMethod());

        Map<String, String> jsonData = parseRequestJSON(request);

        log.info("jsonData: "+jsonData);

        UsernamePasswordAuthenticationToken authenticationToken //UsernamePasswordAuthenticationToken 인증 정보를 만들어서 다음 필터에서 진행
                = new UsernamePasswordAuthenticationToken(
                jsonData.get("mid"),
                jsonData.get("password"));

        return getAuthenticationManager().authenticate(authenticationToken);
    }


    private Map<String,String> parseRequestJSON(HttpServletRequest request) { //POST 방식으로 호출 시 json 문자열을 처리하는 메소드

        //JSON 데이터를 분석해서 mid, mpw 전달 값을 Map으로 처리
        try(Reader reader = new InputStreamReader(request.getInputStream())){

            Gson gson = new Gson();

            return gson.fromJson(reader, Map.class);

        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
