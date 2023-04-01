package com.hallym.festival.service;

import com.hallym.festival.domain.menu.dto.MenuDto;
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
    MenuServicelmpl menuService;

    @Test
    @DisplayName("메뉴 생성")
    public void testCreate() {
        Long BoothID = 1L;
        IntStream.rangeClosed(1,5).forEach(i -> {
            MenuDto menuDto =
                    MenuDto.builder()
                            .name("떡복이" + i)
                            .price(7000L * i).build();
            menuService.create(BoothID, menuDto);
        });
        System.out.println("----------작성완료!----------");
    }

    @Test
    @DisplayName("부스별로 메뉴 목록 조회")
    public void testGetList() throws Exception {
        Long BoothId = 1L;
        List<MenuDto> menuList = menuService.getAll(BoothId);
        System.out.println(menuList.size());
        System.out.println("----------출력완료!----------");
    }

    @Test
    @DisplayName("메뉴 수정")
    public void testUpdate() {
        Long id = 3L;
        MenuDto menuDto =
                MenuDto.builder()
                        .name("부대찌개")
                        .price(15000L)
                        .build();
        menuService.update(id, menuDto);
        System.out.println("----------수정완료!----------");
    }

    @Test
    @DisplayName("메뉴 삭제")
    public void testDelete() {
        Long id = 1L;
        menuService.delete(id);
        System.out.println("----------삭제완료!----------");
    }
}
