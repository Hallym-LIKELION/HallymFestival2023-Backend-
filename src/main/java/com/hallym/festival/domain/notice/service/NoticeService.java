package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.domain.notice.repository.NoticeRepository;
import com.hallym.festival.global.exception.WrongBoothId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<NoticeDto> getNoticeList() {
        List<Notice> noticeList = noticeRepository.findAll();
        return noticeList.stream()
                .map(notice -> notice.toDto())
                .collect(Collectors.toList());
    }

    public NoticeDto getNotice(Long id) {
        Notice notice = findByNotice(id);
        NoticeDto noticeDto = notice.toDto();
        return noticeDto;
    }

    public Notice findByNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new WrongBoothId());
    }

}
