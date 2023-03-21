package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.notice.dto.NoticeRequestDto;
import com.hallym.festival.domain.notice.dto.NoticeResponseDto;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor //의존성 주입 생성자 방법
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public Notice createNotice(NoticeRequestDto noticeRequestDto) {
        Notice notice = noticeRequestDto.toEntity();
        return noticeRepository.save(notice);
    }

}
