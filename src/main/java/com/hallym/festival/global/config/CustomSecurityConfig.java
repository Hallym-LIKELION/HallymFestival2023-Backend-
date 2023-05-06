package com.hallym.festival.global.config;

import com.hallym.festival.global.security.APIUserDetailsService;
import com.hallym.festival.global.security.filter.APILoginFilter;
import com.hallym.festival.global.security.filter.RefreshTokenFilter;
import com.hallym.festival.global.security.filter.TokenCheckFilter;
import com.hallym.festival.global.security.handler.APILoginSuccessHandler;
import com.hallym.festival.global.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //원하는 곳에 어노테이션으로 권한 체크 가능
@RequiredArgsConstructor
public class CustomSecurityConfig  extends WebSecurityConfigurerAdapter {

    private final APIUserDetailsService apiUserDetailsService;
    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("------------web configure-------------------");

        return (web) -> web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations());

    }

    @Bean // AuthenticationManager를 Bean으로 등록
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override //WebSecurityConfigurerAdapter 클래스를 상속받아 configure 메서드를 오버라이딩
    protected void configure(final HttpSecurity http) throws Exception {

        log.info("------------SecurityFilterChain 실행 중-------------------");

        //APILoginFilter 설정
        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken"); //로그인 처리를 하는 경로 지정
        apiLoginFilter.setAuthenticationManager(authenticationManagerBean());

        //APILoginSuccessHandler에 jwtUtil를 주입
        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);

        //APILoginSuccessHandler의 동작이 filter와 연동되도록 설정
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);


        //APILoginFilter의 위치는 UsernamePasswordAuthenticationFilter 앞쪽에 동작하도록 설정
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);

        //api로 시작하는 모든 경로는 TokenCheckFilter 동작
        http.addFilterBefore(
                tokenCheckFilter(jwtUtil, apiUserDetailsService),
                UsernamePasswordAuthenticationFilter.class
        );

        //refreshToken 호출 처리
        http.addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil),
                TokenCheckFilter.class);


        http.csrf().disable(); //csrf 토큰 비활성화

        http.sessionManagement() //세션 사용 안함
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil, APIUserDetailsService apiUserDetailsService) {

        return new TokenCheckFilter(apiUserDetailsService, jwtUtil);
    }

}
