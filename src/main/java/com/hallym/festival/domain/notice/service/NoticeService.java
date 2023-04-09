package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.notice.dto.NoticeDto;

import java.util.List;

public interface NoticeService {
    NoticeDto create(NoticeDto noticeDto);

    PageResponseDTO<NoticeDto> getNoticeListPage(PageRequestDTO pageRequestDTO);

    NoticeDto getNotice(Long id);

    String delete(Long id);

    NoticeDto modify(Long id, NoticeDto noticeDto);

    PageResponseDTO<NoticeDto> searchPage(String keyword, PageRequestDTO pageRequestDTO);


}
