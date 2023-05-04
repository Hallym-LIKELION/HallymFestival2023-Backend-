package com.hallym.festival.repository;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.menu.entity.Menu;
import com.hallym.festival.domain.menu.repository.MenuRepository;
import com.hallym.festival.global.security.Encrypt;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@Transactional
public class MenuRepositoryTests {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    BoothRepository boothRepository;
    @Autowired
    Encrypt encrypt;

    @Test
    @Rollback(value = false)
    public void testInsertBoothAndMenu() {
        Booth booth = boothRepository.save(Booth.builder()
                .booth_title("부스명..." )
                .booth_content("동아리 소개..." )
                .booth_type(BoothType.푸드트럭)
                .writer("부스담당매니저")
                .build());

        Menu menu1 = Menu.builder()
                .name("떡볶이")
                .price(7000L).build();

        Menu menu2 = Menu.builder()
                .name("떡볶이")
                .price(7000L).build();

        menu1.setBooth(booth);
        menu2.setBooth(booth);
        menuRepository.save(menu1);
        menuRepository.save(menu2);

        Assertions.assertThat(booth.getMenus().size()).isEqualTo(2);
    }


}
