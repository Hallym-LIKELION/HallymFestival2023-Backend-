package com.hallym.festival.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.service.BoothService;
import com.hallym.festival.domain.booth.service.BoothServiceImpl;
import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MeunResponseDto;
import com.hallym.festival.domain.menu.service.MenuService;
import com.hallym.festival.domain.menu.service.MenuServicelmpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
@DisplayName("메뉴 서비스 테스트")
public class MenuServiceTests {
    @Autowired
    MenuService menuService;
    @Autowired
    BoothService boothService;

    @Test
    @DisplayName("메뉴 생성")
    public void testCreate() {
        Long bno = 1L;
        IntStream.rangeClosed(1,2).forEach(i -> {
            MenuRequestDto menuRequestDto =
                    MenuRequestDto.builder()
                            .name("떡복이" + i)
                            .price(7000L * i).build();
            menuService.create(bno, menuRequestDto);
        });
        System.out.println("----------작성완료!----------");
    }

    @Test
    @DisplayName("부스별로 메뉴 목록 조회")
    public void testGetList() throws Exception {
        Long bno = 1L;
        List<MeunResponseDto> menuList = menuService.getAll(bno);
        System.out.println(menuList.size());
        System.out.println("----------출력완료!----------");
    }

    @Test
    @DisplayName("메뉴 수정")
    public void testUpdate() {
        Long mno = 1L;
        MenuRequestDto menuRequestDto =
                MenuRequestDto.builder()
                        .name("부대찌개")
                        .price(15000L)
                        .build();
        menuService.modify(mno, menuRequestDto);
        System.out.println("----------수정완료!----------");
    }

    @Test
    @DisplayName("메뉴 삭제")
    public void testDelete() {
        Long mno = 1L;
        menuService.delete(mno);
        System.out.println("----------삭제완료!----------");
    }

    @Test
    public void testInsertBoothAndMenu() {
        BoothDTO boothDTO = BoothDTO.builder()
                .booth_title("타코야끼")
                .booth_content("새벽 4시 45분")
                .writer("주펄")
                .booth_type(BoothType.푸드트럭)
                .build();

        Long bno = boothService.register(boothDTO);

        MenuRequestDto menuRequestDto =
                MenuRequestDto.builder()
                        .name("떡복이")
                        .price(7000L).build();

        menuService.create(bno, menuRequestDto);

    }
}
