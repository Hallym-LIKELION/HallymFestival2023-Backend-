package com.hallym.festival.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
public class RestCorsConfig {
        @Bean
        public CorsFilter corsFilter() {

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();

            config.setAllowCredentials(true); // 서버 응답 시 JSON 허용
            config.addAllowedOriginPattern("*"); //다른 포트 번호에 대한 응답 허용
            config.addAllowedHeader("*"); //모든 헤더에 대해 요청 허용
            config.addAllowedMethod("*"); //모든 Http Method에 대해 요청 허용
            config.addExposedHeader("Authorization"); //JWT Token을 이용해 인증을 하기 위한 커스텀 헤더 추가

            source.registerCorsConfiguration("/**", config); //모든 url에 대해 위 설정 정보들을 적용
            return new CorsFilter(source);
        }
    }


