package com.hallym.festival.util;

/**
 *  [o] 1. JWTUtil 사용해서 문자열 생성
 *  [o] 2. 생성된 JWT 문자열을 정상적인지 검사
 *  [o] 3. validateToken()을 통해서 사이트 검사결과랑 일치하는지 확인
 * **/

import com.hallym.festival.global.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Log4j2
public class JWTUtilTests {

    @Autowired
    private JWTUtil jwtUtil;


    @DisplayName("JWT 생성 테스트")
    @Test
    public void testGenerate() {

        Map<String, Object> claimMap = Map.of("mid","ABCDE");

        String jwtStr = jwtUtil.generateToken(claimMap,1);

        log.info(jwtStr);
    }

    @DisplayName("Jwts.parser()를 통한 예외 처리 테스트")
    @Test
    public void testValidate() {

        //유효시간이 지난 토큰
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODMzNDEzMjYsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNㅓjgzMzQxMjY2fQ.7X-nmNpJ4oltfGYRuKwqOvQY6v-90Txzh0-K2_ohzHo";

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr); //Jwts.parser()를 통해 예외를 던짐

        log.info(claim);

    }

    @DisplayName("JWT 유효 시간 테스트")
    @Test
    public void testAll() {

        String jwtStr = jwtUtil.generateToken(Map.of("mid","20154344","phone","010-5261-1234"),1);

        log.info(jwtStr);

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info("MID: " + claim.get("mid"));

        log.info("PHONE: " + claim.get("phone"));

    }

}