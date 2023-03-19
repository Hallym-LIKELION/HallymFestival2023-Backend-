package com.hallym.festival.global.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean //메소드 실행 결과로 반환된 객체를 스프링의 빈으로 등록
    public ModelMapper getMapper() { // modelMapper를 반환하는 메소드
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE) // LOOSE 전략
                .setSkipNullEnabled(true) // null인 필드는 skip
                .setFieldMatchingEnabled(true) //private으로 선언된 인스턴스 변수에 접근 허용
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); //Access level PRIVATE 으로 변경

        return modelMapper;
    }

    // setter 없이도 필드명이 같을 때 자동 매핑 처리합니다.
}
