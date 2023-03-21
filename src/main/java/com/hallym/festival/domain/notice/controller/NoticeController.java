package com.hallym.festival.domain.notice.controller;

import com.hallym.festival.domain.notice.dto.NoticeRequestDto;
import com.hallym.festival.domain.notice.dto.NoticeResponseDto;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public void createNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
        System.out.println(noticeRequestDto);
    }
}
