package com.hallym.festival.booth;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.service.BoothService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoothServiceTests {

    @Autowired
    BoothService boothService;

    @Test
    public void testResister(){

        BoothDTO boothDTO = BoothDTO.builder()
                .booth_title("3점왕 왕좌에 올라라")
                .booth_content("지정된 3점 슛 라인에서 공 10개를 던져서 가장 많이 넣은 제왕을 뽑습니다.")
                .writer("레이업애호가")
                .booth_type("게임")
                .build();

        boothService.register(boothDTO);
    }

}
