package com.hallym.festival.service;

import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MenuResponseDto;
import com.hallym.festival.domain.menu.service.MenuService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MenuServiceTests {
    @Autowired
    MenuService menuService;

    @Test
    public void 메뉴_생성() {
        Long BoothID = 1L;
        IntStream.rangeClosed(1,5).forEach(i -> {
            MenuRequestDto menuRequestDto =
                    MenuRequestDto.builder()
                            .name("떡복이" + i)
                            .price(7000L * i).build();
            menuService.create(BoothID, menuRequestDto);
        });
        System.out.println("----------작성완료!----------");
    }

    @Test
    public void 메뉴_목록() throws Exception {
        Long BoothId = 1L;
        List<MenuResponseDto> menuList = menuService.getAll(BoothId);
        System.out.println(menuList.size());
        System.out.println("----------출력완료!----------");
    }

/*    @Test
    public void 메뉴_수정() {
        Long id = 1L;
        MenuRequestDto menuRequestDto =
                MenuRequestDto.builder()
                        .name("떡복이세트(떡복이+튀김)")
                        .price(10000L).build();
        menuService.update(id, menuRequestDto);
        System.out.println("----------수정완료!----------");
    }*/

    @Test
    public void 메뉴_삭제() {
        Long id = 1L;
        menuService.delete(id);
        System.out.println("----------삭제완료!----------");
    }
}
