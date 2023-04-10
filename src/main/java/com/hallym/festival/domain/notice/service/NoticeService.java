package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.notice.dto.NoticeDto;

import java.util.List;

public interface NoticeService {
    NoticeDto create(NoticeDto noticeDto);

    PageResponseDTO<NoticeDto> getNoticeListPage(PageRequestDTO pageRequestDTO);

    NoticeDto getNotice(Long nno);

    String delete(Long nno);

    NoticeDto modify(Long nno, NoticeDto noticeDto);

    PageResponseDTO<NoticeDto> searchPage(String keyword, PageRequestDTO pageRequestDTO);


}
