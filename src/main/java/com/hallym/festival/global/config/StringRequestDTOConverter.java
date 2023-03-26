package com.hallym.festival.global.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hallym.festival.domain.booth.dto.BoothDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Component
@Log4j2
public class StringRequestDTOConverter extends Throwable implements Converter<String, BoothDTO> { //Throwable 인터페이스. String -> BoothDTO 타입변환

    ObjectMapper objectMapper = new ObjectMapper(); //JSON 객체를 deserialization하기 위한 클래스
    BoothDTO boothDTO; //역직렬화 시킬 클래스

    public StringRequestDTOConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public BoothDTO convert(String convertTarget) {
        try{
            boothDTO = objectMapper.readValue(convertTarget, new TypeReference<BoothDTO>() { // 변환할 대상 , BoothDTO 클래스 타입으로 변환 후 레퍼런스 반환
            });
        }catch (JsonProcessingException e){
            log.info(e);
        }
        return boothDTO;
    }
}
