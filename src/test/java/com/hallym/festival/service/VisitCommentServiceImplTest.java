package com.hallym.festival.service;


import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentPasswordDto;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentRequestDto;
import com.hallym.festival.domain.visitcomment.service.VisitCommentServiceImpl;
import com.hallym.festival.global.security.Encrypt;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class VisitCommentServiceImplTest {

    @Autowired
    VisitCommentServiceImpl visitCommentService;
    @Autowired
    Encrypt encrypt;

    @Test
    @Rollback(value = false)
    public void VisitCommentInsert() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        IntStream.rangeClosed(1, 30).forEach(i -> {
            VisitCommentRequestDto visitCommentRequestDto = VisitCommentRequestDto.builder()
                    .content("방명록입니다.." + i)
                    .password("1234")
                    .build();

            visitCommentService.create(visitCommentRequestDto, request);
        });
    }

    @Test
    @Rollback(value = false)
    public void VisitCommentDelete() {
        Long vno = 1L;
        VisitCommentPasswordDto password = VisitCommentPasswordDto.builder()
                .password("1234")
                .build();

        String result = visitCommentService.delete(vno, password);
        log.info(result);
    }
}