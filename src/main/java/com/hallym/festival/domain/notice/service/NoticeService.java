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
        notice.setActive(Boolean.TRUE);
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


    @Transactional
    public String delete(Long id) {
        Notice notice = findByNotice(id);
        notice.setActive(Boolean.FALSE);
        noticeRepository.save(notice);
        return "delete success";
    }

    public NoticeDto update(Long id, NoticeDto noticeDto) {
        Notice notice = findByNotice(id);
        Notice newnotice = noticeDto.toEntity();
        notice.updateNotice(newnotice);
        noticeRepository.save(notice);
        NoticeDto noticeDto1 = notice.toDto();
        return  noticeDto1;
    }

    @Transactional
    public List<NoticeDto> search(String keyword) {
        List<Notice> noticeList = noticeRepository.findByTitleContaining(keyword);
        List<NoticeDto> noticeDtoList = new ArrayList<>();
        if(noticeList.isEmpty()) return noticeDtoList;
        for (Notice notice : noticeList) {
            NoticeDto noticeDto = notice.toDto();
            noticeDtoList.add(noticeDto);
        }
        return noticeDtoList;
    }

    public Notice findByNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new WrongBoothId());
    }

}
