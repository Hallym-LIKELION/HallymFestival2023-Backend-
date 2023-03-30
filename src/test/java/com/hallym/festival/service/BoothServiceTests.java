package com.hallym.festival.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.entity.BoothType;
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
    public void testRegister(){

//        BoothServiceImpl을 감싸서 만든 클래스인지 확인
//        log.info(boothService.getClass().getName()); // BoothServiceImpl$$EnhancerBySpringCGLIB$$75889ac5

        BoothDTO boothDTO = BoothDTO.builder()
                .booth_title("타코야끼")
                .booth_content("새벽 4시 45분")
                .writer("주펄")
                .booth_type(BoothType.푸드트럭)
                .build();

        Long bno = boothService.register(boothDTO);

        log.info("bno: " + bno);
    }

    @Test
    public void testModify() {

        //변경에 필요한 데이터만
        BoothDTO boothDTO = BoothDTO.builder()
                .bno(5L)
                .booth_title("수정 제목")
                .booth_content("수정 내용")
                .writer("수정 작성자")
                .booth_type(BoothType.플리마켓)
                .build();

        boothService.modify(boothDTO);

        log.info(boothDTO);
    }

    @Test
    public void testGetOne(){

        Long bno = 2L;

        BoothDTO boothDTO = boothService.getOne(bno);

        log.info(boothDTO);

    }

    @Test
    public void testRemove(){

        Long bno = 2L;

        boothService.remove(bno);

        log.info(boothService.getOne(bno));
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoothDTO> responseDTO = boothService.list(pageRequestDTO);

        log.info(responseDTO);

    }
}
