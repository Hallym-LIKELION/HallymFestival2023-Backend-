package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class CommentServiceTest {

    @Autowired CommentService commentService;

    @Test
    public void create() throws Exception {
        //when
        MockHttpServletRequest request = new MockHttpServletRequest();
        Long bno = 1L;
        CommentRequestDto commentRequestDto =
                CommentRequestDto.builder()
                .content("댓글입니다.").build();

        CommentResponseDto commentResponseDto = commentService.create(bno, commentRequestDto, request);
        //then
        log.info(commentResponseDto);

    }
}