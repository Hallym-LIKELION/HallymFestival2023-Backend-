package com.hallym.festival.service;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class NoticeServiceTests {
    @Autowired
    NoticeService noticeService;

    @Test
    public void 게시물_작성() {
        IntStream.rangeClosed(1,10).forEach(i -> {

            NoticeDto noticeDto =
                    NoticeDto.builder()
                            .title("제목" + i)
                            .content("내용" + i)
                            .build();

            noticeService.create(noticeDto);
        });
        System.out.println("----------작성완료!----------");
    }
}
