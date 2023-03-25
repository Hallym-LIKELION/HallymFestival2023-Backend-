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

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteNotice(@PathVariable("id") Long id) {
        String result = noticeService.delete(id);
        return Map.of("result", result);
    }

    @PutMapping("/{id}")
    public Map<String, String> updateNotice(@RequestBody NoticeDto noticeDto, @PathVariable("id") Long id) {
        noticeService.update(id, noticeDto);
        return Map.of("result", "update success");
    }

    @GetMapping("/search")
    public List<NoticeDto> search(@RequestParam(value = "keyword") String keyword) {
        List<NoticeDto> noticeDtoList = noticeService.search(keyword);
        return noticeDtoList;
    }
}
