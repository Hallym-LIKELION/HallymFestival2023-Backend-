package com.hallym.festival.service;

import com.hallym.festival.domain.booth.dto.*;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.entity.DayNight;
import com.hallym.festival.domain.booth.service.BoothService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
@DisplayName("부스 서비스 테스트")
public class BoothServiceTests {

    @Autowired
    BoothService boothService;

    @DisplayName("부스 데이터 등록 테스트")
    @Test
    public void testRegister() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            BoothDTO boothDTO = BoothDTO.builder()
                    .booth_title("부스 제목"+i)
                    .booth_content("부스 콘텐츠"+i)
                    .writer("2015434"+i)
                    .booth_type(BoothType.주점)
                    .dayNight(DayNight.DAY)
                    .openDay("[1,2,3]")
                    .build();

            Long bno = boothService.register(boothDTO);

            log.info("bno: " + bno);
        });
    }

    @DisplayName("이미지를 포함한 등록 테스트")
    @Test
    public void testRegisterWithImages() {

        log.info(boothService.getClass().getName());

        BoothDTO boothDTO = BoothDTO.builder()
                .booth_title("파일 첨부 게시글2")
                .booth_content("테스트입니다.")
                .writer("20154342")
                .booth_type(BoothType.플리마켓)
                .dayNight(DayNight.NIGHT)
                .openDay("[2,3]")
                .build();

        boothDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg",
                        UUID.randomUUID()+"_bbb.jpg",
                        UUID.randomUUID()+"_bbb.jpg"
                ));

        Long bno = boothService.register(boothDTO);

        log.info("bno: " + bno);
    }

    @DisplayName("특정 부스 수정 테스트")
    @Test
    public void testModify() {

        //변경에 필요한 데이터만
        BoothDTO boothDTO = BoothDTO.builder()
                .booth_title("수정 제목")
                .booth_content("수정 내용")
                .writer("수정 작성자")
                .booth_type(BoothType.주점)
                .dayNight(DayNight.NIGHT)
                .openDay("[1]")
                .build();

        boothDTO.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));

        boothService.modify(3L,boothDTO);

        log.info(boothDTO);
    }


    @DisplayName("특정 부스 상세 조회")
    @Test
    public void testGetOne(){

        Long bno = 2L;

        BoothDTO boothDTO = boothService.getOne(bno);

        log.info(boothDTO);

    }

    @DisplayName("특정 부스 삭제 테스트(is_deleted가 true로 변경)")
    @Test
    public void testRemove(){

        Long bno = 100L;

        boothService.remove(bno);

    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoothListAllDTO> responseDTO = boothService.listWithAll(pageRequestDTO);

        log.info(responseDTO);

    }

    @Test
    public void testReadAll(){

        Long bno = 2L;

        BoothDTO boothDTO = boothService.getOne(bno);

        log.info(boothDTO);

        for(String fileName : boothDTO.getFileNames()){
            log.info(fileName);
        }//end for
    }

    @DisplayName("목록 데이터 조회 테스트")
    @Test
    public void testListWithAll() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoothListAllDTO> responseDTO =
                boothService.listWithAll(pageRequestDTO);

        List<BoothListAllDTO> dtoList = responseDTO.getDtoList();

        dtoList.forEach(boothListAllDTO -> {
            log.info(boothListAllDTO.getBno()+":"+boothListAllDTO.getBooth_title());

            if(boothListAllDTO.getBoothImages() != null) {
                for (BoothImageDTO boothImageDTO : boothListAllDTO.getBoothImages()) {
                    log.info(boothImageDTO);
                }
            }

            log.info("-------------------------------");
        });
    }
}
