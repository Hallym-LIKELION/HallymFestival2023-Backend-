package com.hallym.festival.service;


import com.hallym.festival.domain.visitcomment.dto.VisitCommentPasswordDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentRequestDTO;
import com.hallym.festival.domain.visitcomment.entity.Color;
import com.hallym.festival.domain.visitcomment.service.VisitCommentServiceImpl;
import com.hallym.festival.global.security.Encrypt;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;

import java.util.stream.IntStream;

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
            VisitCommentRequestDTO visitCommentRequestDto = VisitCommentRequestDTO.builder()
                    .content("방명록입니다.." + i)
                    .password("1234")
                    .color(Color.PINK)
                    .build();

            visitCommentService.create(visitCommentRequestDto, request);
        });
    }

    @Test
    @Rollback(value = false)
    public void VisitCommentDelete() {
        Long vno = 1L;
        VisitCommentPasswordDTO password = VisitCommentPasswordDTO.builder()
                .password("1234")
                .build();

        String result = visitCommentService.delete(vno, password);
        log.info(result);
    }
}