package com.hallym.festival.service;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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

    @Test
    public void 게시물_목록_조회() {
        List<NoticeDto> noticeDtoList = noticeService.getNoticeList();
        System.out.println(noticeDtoList.size());
        for (NoticeDto notice : noticeDtoList) {
            System.out.println(notice);
        }
    }

    @Test
    public void 게시물_한개_조회() {
        Long id = 1L;
        NoticeDto noticeDto = noticeService.getNotice(id);
        System.out.println(noticeDto);
    }

    @Test
    public void 게시물_수정() {

        NoticeDto noticeDto =
                NoticeDto.builder()
                        .title("제목")
                        .content("내용")
                        .build();

        noticeService.create(noticeDto);

        Long id = 1L;
        String title = "수정제목";
        String content = "수정내용";
        NoticeDto newNoticeDto =
                NoticeDto.builder()
                        .title(title)
                        .content(content)
                        .build();
        noticeService.update(id, newNoticeDto);

        System.out.println("----------수정 완료----------");
    }

    NoticeDto noticeDto;
    @Test
    public void 게시물_제목수정() {

        NoticeDto noticeDto =
                NoticeDto.builder()
                        .title("제목")
                        .content("내용")
                        .build();

        noticeService.create(noticeDto);

        Long id = 1L;
        String title = "수정제목";
        String content = noticeDto.getContent();
        NoticeDto newNoticeDto =
                NoticeDto.builder()
                        .title(title)
                        .content(content)
                        .build();
        noticeService.update(id, newNoticeDto);
        System.out.println("----------수정 완료----------");
    }

    @Test
    public void 게시물_내용수정() {

        NoticeDto noticeDto =
                NoticeDto.builder()
                        .title("제목")
                        .content("내용")
                        .build();

        noticeService.create(noticeDto);

        Long id = 1L;
        String title = noticeDto.getTitle();
        String content = "수정내용";
        NoticeDto newNoticeDto =
                NoticeDto.builder()
                        .title(title)
                        .content(content)
                        .build();
        noticeService.update(id, newNoticeDto);
        System.out.println("----------수정 완료----------");
    }

    @Test
    public void 게시물_검색성공() {
        IntStream.rangeClosed(1,10).forEach(i -> {
            NoticeDto noticeDto =
                    NoticeDto.builder()
                            .title("제목" + i)
                            .content("내용" + i)
                            .build();
            noticeService.create(noticeDto);
        });
        String keyword = "제";
        noticeService.search(keyword);
        System.out.println("----------검색 성공----------");
    }

    @Test
    public void 게시물_검색실패() {
        IntStream.rangeClosed(1,10).forEach(i -> {
            NoticeDto noticeDto =
                    NoticeDto.builder()
                            .title("제목" + i)
                            .content("내용" + i)
                            .build();
            noticeService.create(noticeDto);
        });
        String keyword = "11";
        noticeService.search(keyword);
        System.out.println("----------검색 실패----------");
    }
}
