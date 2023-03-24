package com.hallym.festival.domain.notice.controller;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public Map<String, String> create(@RequestBody NoticeDto noticeDto) {
        noticeService.create(noticeDto);
        return Map.of("result", "create success");
    }

    @GetMapping
    public List<NoticeDto> getNoticeList() {
        return noticeService.getNoticeList();
    }

    @GetMapping("/{id}")
    public NoticeDto getNotice(@PathVariable("id") Long id) {
        return noticeService.getNotice(id);
    }

}
