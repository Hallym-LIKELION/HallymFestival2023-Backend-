package com.hallym.festival.domain.notice.controller;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping //여러개 게시물 보여줌
    public List<NoticeDto> getNotices() throws Exception {
        return noticeService.getNotices();
    }

    @GetMapping("/{id}") //한개의 게시물 보여줌
    public NoticeDto getNotice(@PathVariable("id") Long id) throws Exception {
        return noticeService.getNotice(id);
    }

    // admin 도메인 추가 될 때 수정
/*
    @PostMapping //게시물 작성
    public NoticeDto write(@RequestBody NoticeDto noticeDto) {
        Admin admin = admin.findById(1).get();
        return noticeService.write(noticeDto, admin);
    }
*/
/*
    @PutMapping("/{id}") // 게시물 수정
    public NoticeDto updateNotice(@PathVariable("id") Long id, @RequestBody NoticeDto noticedto) throws Exception{
        Admin admin = admin.findById(1).get();
        return noticeService.updateNotice(id, noticedto);
    }
*/

/*
    @PutMapping("/{id}") // 게시물 삭제
    public NoticeDto updateNotice(@PathVariable("id") Long id, @RequestBody NoticeDto noticedto) throws Exception{
        Admin admin = admin.findById(1).get();
        return noticeService.updateNotice(id, noticedto);
    }
*/

    @GetMapping("/search")
    public List<NoticeDto> search(String keyword, NoticeDto noticeDto) {
        List<NoticeDto> searchList = noticeService.serarch(keyword);
        return searchList;
    }

    //@GetMapping("/paging?={page}") //잘 모르겠다.
}
