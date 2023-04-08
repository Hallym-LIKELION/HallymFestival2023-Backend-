package com.hallym.festival.domain.notice.controller;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.service.NoticeServicelmpl;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeServicelmpl noticeService;

    @PostMapping
    public Map<String, String> createNotice(@RequestBody NoticeDto noticeDto) {
        noticeService.create(noticeDto);
        return Map.of("result", "create success");
    }

//    @GetMapping
//    public List<NoticeDto> getNoticeList() {
//        return noticeService.getNoticeList();
//    }

    @GetMapping
    public PageResponseDTO<NoticeDto> getNoticeListPage(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<NoticeDto> responseDTO = noticeService.getNoticeListPage(pageRequestDTO);
        return responseDTO;
    }

    @GetMapping("/{id}")
    public NoticeDto getNotice(@PathVariable("id") Long id) {
        return noticeService.getNotice(id);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteNotice(@PathVariable("id") Long id) {
        String result = noticeService.delete(id);
        return Map.of("result", result);
    }

    @PutMapping("/{id}")
    public Map<String, String> updateNotice(@RequestBody NoticeDto noticeDto, @PathVariable("id") Long id) {
        noticeService.update(id, noticeDto);
        return Map.of("result", "update success");
    }

//    @GetMapping("/search")
//    public List<NoticeDto> search(@RequestParam(value = "keyword") String keyword) {
//        List<NoticeDto> noticeDtoList = noticeService.search(keyword);
//        return noticeDtoList;
//    }

    @GetMapping("/search")
    public PageResponseDTO<NoticeDto> searchPage(@RequestParam(value = "keyword") String keyword, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<NoticeDto> responseDTO = noticeService.searchPage(keyword, pageRequestDTO);
        return responseDTO;
    }

}
