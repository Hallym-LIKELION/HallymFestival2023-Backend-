package com.hallym.festival.global.security.util;

import com.hallym.festival.domain.Users.APIUserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    @Autowired
    private APIUserService apiUserService;

    @Value("${com.hallym.festival.jwt.secret}")
    private String key;

    public String generateToken(Map<String, Object> valueMap, int days){ //토큰을 생성하는 기능

        log.info("----------generateKey... 시크릿키 : " + key);

        String mid = (String) valueMap.get("mid");

        log.info(mid);

        String roleSet = apiUserService.getRoleSetByMid(mid);
        log.info("----------------" + roleSet);

        //헤더 부분
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ","JWT");
        headers.put("alg","HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);
        payloads.put("role", roleSet); // role_set 정보 추가

        //테스트 시에는 짧은 유효 기간
        int time = (60*24) * days; //테스트는 분단위로 나중에 60*24 (일)단위변경

        //10분 단위로 조정
//        int time = (10) * days; //테스트는 분단위로 나중에 60*24 (일)단위변경

        String jwtStr = Jwts.builder() //Jwts 빌더 사용
                .setHeader(headers) //헤더 지정
                .setClaims(payloads) //페이로드 지정
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant())) //발생시칸
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant())) //여기 Minnutes 부분 plusDays로 추후 변경
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) //서명
                .compact();

        return jwtStr; //jwt문자열 생성!!
    }


    public Map<String, Object> validateToken(String token)throws JwtException { //토큰을 검증하는 기능

        Map<String, Object> claim = null;

        claim = Jwts.parser() //Jwts.parser()를 이용해 문제 발생 시 예외를 던진다.
                .setSigningKey(key.getBytes()) // Set Key
                .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                .getBody();
        return claim;
    }

}