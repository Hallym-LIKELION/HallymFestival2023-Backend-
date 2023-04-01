package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.notice.dto.NoticeDto;

import java.util.List;

public interface NoticeService {
    NoticeDto create(NoticeDto noticeDto);

    List<NoticeDto> getNoticeList();

    NoticeDto getNotice(Long id);

    String delete(Long id);

    NoticeDto update(Long id, NoticeDto noticeDto);

    List<NoticeDto> search(String keyword);


}
