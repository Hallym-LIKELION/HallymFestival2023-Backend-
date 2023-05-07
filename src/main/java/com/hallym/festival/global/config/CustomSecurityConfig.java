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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //원하는 곳에 어노테이션으로 권한 체크 가능
@RequiredArgsConstructor
public class CustomSecurityConfig {

    private final APIUserDetailsService apiUserDetailsService; //사용자 인증 정보를 제공하는 클래스
    private final JWTUtil jwtUtil; //JWT를 생성/검증하는 유틸 클래스

    @Bean
    public PasswordEncoder passwordEncoder() { //패스워드 암호호 인코더
        return new BCryptPasswordEncoder();
    }

    @Bean //Spring Security가 정적 리소스에 대한 보안 검사를 수행하지 않도록 WebSecurityCustomizer 구현
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("------------web configure-------------------");

        return (web) -> web.ignoring() //지정된 요청 경로에 대해 보안 검사를 미수행
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations());
                        //Spring Boot에서 기본적으로 제공하는 정적 리소스 경로
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception { //HttpSecurity 객체를 매개변수로 받아서 SecurityFilterChain 구성

        log.info("------------SecurityFilterChain 실행 중-------------------");

        //AuthenticationManager Setting
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);  //HttpSecurity 객체에서 AuthenticationManagerBuilder 인스턴스 획득
        authenticationManagerBuilder.userDetailsService(apiUserDetailsService).passwordEncoder(passwordEncoder());  //AuthenticationManagerBuilder에서 passwordEncoder()를 사용하여 비밀번호를 암호화하고 비밀번호 검증


        //Get Built AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();


        //HttpSecurity 객체에 AuthenticationManager 설정
        http.authenticationManager(authenticationManager);


        //APILoginFilter 설정
        APILoginFilter apiLoginFilter = new APILoginFilter("/login"); //로그인 처리를 하는 url 지정
        apiLoginFilter.setAuthenticationManager(authenticationManager); //해당 url로 들어오는 로그인 요청을 처리할 때, APILoginFilter가 authenticationManager를 사용하여 사용자 인증을 수행


        //APILoginSuccessHandler에 jwtUtil를 주입
        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil); //successHandler 객체는 로그인 성공 시 호출되는 onAuthenticationSuccess() 메소드에서 JWT를 생성하고, 해당 JWT를 사용자에게 반환


        //APILoginSuccessHandler의 동작이 filter와 연동되도록 설정
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);
        //APILoginFilter 객체의 setAuthenticationSuccessHandler() 메소드를 사용하여, APILoginSuccessHandler 객체와 연동하도록 설정


        //APILoginFilter의 위치는 UsernamePasswordAuthenticationFilter 앞쪽에 동작하도록 설정
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);
        //로그인 요청이 UsernamePasswordAuthenticationFilter에서 처리되기 전에 APILoginFilter가 실행
        //APILoginFilter에서 생성된 JWT를 UsernamePasswordAuthenticationFilter에서 로그인 성공 시 생성되는 기본 세션 대신 사용


        //api로 시작하는 모든 경로는 TokenCheckFilter 동작
        http.addFilterBefore(
                tokenCheckFilter(jwtUtil, apiUserDetailsService),
                UsernamePasswordAuthenticationFilter.class
        );

        //refreshToken 호출 처리
        http.addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil),
                TokenCheckFilter.class);


        http.csrf().disable(); //csrf 토큰 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션을 사용하지 않음

        http.cors(httpSecurityCorsConfigurer -> { //CORS 구성
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        return http.build(); //설정된 보안 구성을 바탕으로 SecurityFilterChain 객체를 생성
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() { //CorsConfigurationSource 인터페이스 구현
        CorsConfiguration configuration = new CorsConfiguration(); // Bean 생성
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); //요청을 보내는 모든 도메인 허용
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE")); //허용할 요청 메소드 설정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); //요청 허용할 헤더
        configuration.setAllowCredentials(true); //인증 정보를 담은 요청도 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //CorsConfiguration 객체를 URL 패턴에 매핑하여 CORS 구성을 지원하는 클래스
        source.registerCorsConfiguration("/**", configuration); //모든 경로에 대한 CORS 설정을 위에서 만든 CorsConfiguration 객체로 등록
        return source;
    }


    //인증된 사용자만이 API 요청에 접근할 수 있도록 JWT 토큰을 검증하는 필터
    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil, APIUserDetailsService apiUserDetailsService) {

        return new TokenCheckFilter(apiUserDetailsService, jwtUtil);
    }

}
