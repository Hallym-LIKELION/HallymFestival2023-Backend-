package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeDto create(NoticeDto noticeDto) {
        Notice notice = noticeDto.toEntity();
        noticeRepository.save(notice);
        NoticeDto noticeDto1 = notice.toDto();
        return noticeDto1;
    }

}
