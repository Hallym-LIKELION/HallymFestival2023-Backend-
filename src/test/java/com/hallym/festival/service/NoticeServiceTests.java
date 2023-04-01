package com.hallym.festival.service;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.service.NoticeServicelmpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
@DisplayName("공지사항 서비스 테스트")
public class NoticeServiceTests {
    @Autowired
    NoticeServicelmpl noticeService;

    @Test
    @DisplayName("게시물 작성")
    public void testCreate() {
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
    @DisplayName("게시물 목록 조회")
    public void testGetList() {
        List<NoticeDto> noticeDtoList = noticeService.getNoticeList();
        System.out.println(noticeDtoList.size());
        for (NoticeDto notice : noticeDtoList) {
            System.out.println(notice);
        }
    }

    @DisplayName("게시물 한개 조회")
    @Test
    public void testGetOne() {
        Long id = 1L;
        NoticeDto noticeDto = noticeService.getNotice(id);
        System.out.println(noticeDto);
    }

    @Test
    @DisplayName("게시물 수정")
    public void testUpdate() {

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

    @Test
    @DisplayName("게시물 제목수정")
    public void testTitleUpdate() {

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
    @DisplayName("게시물 내용수정")
    public void testContentUpdate() {

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
    @DisplayName("게시물 검색성공")
    public void testSearchSuccess() {
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
    @DisplayName("게시물 검색실패")
    public void testSearchFailure() {
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

    @Test
    @DisplayName("게시물 삭제")
    public void testDelete() {
        Long id = 1L;
        noticeService.delete(id);
        System.out.println("----------삭제완료!----------");
    }
}
