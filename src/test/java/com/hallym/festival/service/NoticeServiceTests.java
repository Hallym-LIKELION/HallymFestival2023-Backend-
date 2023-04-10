package com.hallym.festival.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.service.NoticeService;
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
    NoticeService noticeService;

    @Test
    @DisplayName("게시물 작성")
    public void testCreate() {
        IntStream.rangeClosed(1,2).forEach(i -> {

            NoticeDto noticeDto =
                    NoticeDto.builder()
                            .title("제목" + i)
                            .content("내용" + i)
                            .build();

            noticeService.create(noticeDto);
        });
        System.out.println("----------작성완료!----------");
    }

    @DisplayName("게시물 한개 조회")
    @Test
    public void testGetOne() {
        Long nno = 1L;
        NoticeDto noticeDto = noticeService.getNotice(nno);
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

        Long nno = 1L;
        String title = "수정제목";
        String content = "수정내용";
        NoticeDto newNoticeDto =
                NoticeDto.builder()
                        .title(title)
                        .content(content)
                        .build();
        noticeService.modify(nno, newNoticeDto);

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

        Long nno = 1L;
        String title = "수정제목";
        String content = noticeDto.getContent();
        NoticeDto newNoticeDto =
                NoticeDto.builder()
                        .title(title)
                        .content(content)
                        .build();
        noticeService.modify(nno, newNoticeDto);
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

        Long nno = 1L;
        String title = noticeDto.getTitle();
        String content = "수정내용";
        NoticeDto newNoticeDto =
                NoticeDto.builder()
                        .title(title)
                        .content(content)
                        .build();
        noticeService.modify(nno, newNoticeDto);
        System.out.println("----------수정 완료----------");
    }

    @Test
    @DisplayName("게시물 삭제")
    public void testDelete() {
        Long nno = 1L;
        noticeService.delete(nno);
        System.out.println("----------삭제완료!----------");
    }

    @Test
    @DisplayName("게시물 조회 페이징 처리")
    public void testPaging() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<NoticeDto> responseDTO = noticeService.getNoticeListPage(pageRequestDTO);

        log.info(responseDTO);
        System.out.println("----------출력완료!----------");
    }

    @Test
    @DisplayName("게시물 검색 페이징 처리")
    public void testSearchPaging() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        String keyword = "제목";

        PageResponseDTO<NoticeDto> responseDTO = noticeService.searchPage(keyword, pageRequestDTO);

        System.out.println("----------검색완료!----------");
    }
}
