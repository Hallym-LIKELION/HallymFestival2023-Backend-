package com.hallym.festival.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.comment.dto.CommentPasswordDTO;
import com.hallym.festival.domain.comment.dto.CommentRequestDTO;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import com.hallym.festival.domain.comment.service.CommentServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class CommentServiceTest {

    @Autowired
    CommentServiceImpl commentServiceImpl;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BoothRepository boothRepository;

    @Test
    public void create() {
        //when
        MockHttpServletRequest request = new MockHttpServletRequest();
        Long boothId = 1L;
        IntStream.rangeClosed(1,30).forEach(i -> {
            CommentRequestDTO commentRequestDto =
                    CommentRequestDTO.builder()
                            .password("jyp1234")
                            .content("댓글입니다."+i).build();

            commentServiceImpl.create(boothId, commentRequestDto, request);
        });
    }

    @Test
    public void delete() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            if(i%2 == 0) {
                Long boothId = (long) i;
                CommentPasswordDTO pDto = CommentPasswordDTO.builder().password("jyp1234").build();
                commentServiceImpl.delete(boothId, pDto);
            }
        });
    }

    @Test
    @Rollback(value = false)
    public void insertTests() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        Booth booth = Booth.builder()
                        .booth_title("booth_title")
                                .booth_content("booth_content")
                                        .booth_type(BoothType.부스)
                                                .writer("박주영")
                                                        .build();

        boothRepository.save(booth);

        Comment comment1 = Comment.builder()
                .password("1234")
                .content("안녕하세요")
                .ip("123-123-123")
                .booth(booth)
                .build();

        log.info("------------------");
        log.info(booth);

        Comment savedComment1 = commentRepository.save(comment1);


        log.info("------Comment1------");
        log.info(savedComment1.getIp());
        log.info(savedComment1.getBooth());
        log.info(savedComment1.getContent());

    }

}