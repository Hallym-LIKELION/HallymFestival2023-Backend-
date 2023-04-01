package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.domain.notice.repository.NoticeRepository;
import com.hallym.festival.global.exception.WrongBoothId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class NoticeServicelmpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    public NoticeDto create(NoticeDto noticeDto) {
        Notice notice = modelMapper.map(noticeDto, Notice.class); //toEntity
        notice.setActive(Boolean.TRUE);
        noticeRepository.save(notice);
        return modelMapper.map(notice, NoticeDto.class); //toDto
    }

    public List<NoticeDto> getNoticeList() {
        List<Notice> noticeList = noticeRepository.findAllByActiveOrderByRegDateDesc(Boolean.TRUE);
        return noticeList.stream()
                .map(notice -> toDto(notice))
                .collect(Collectors.toList());
    }

    public NoticeDto getNotice(Long id) {
        Notice notice = findByNotice(id);
        return modelMapper.map(notice, NoticeDto.class);
    }


    @Transactional
    public String delete(Long id) {
        Notice notice = findByNotice(id);
        notice.setActive(Boolean.FALSE);
        return "delete success";
    }

    public NoticeDto update(Long id, NoticeDto noticeDto) {
        Notice notice = findByNotice(id);
        Notice newnotice = modelMapper.map(noticeDto, Notice.class);
        notice.updateNotice(newnotice);
        noticeRepository.save(notice);
        return  modelMapper.map(notice, NoticeDto.class);
    }

    @Transactional
    public List<NoticeDto> search(String keyword) {
        List<Notice> noticeList = noticeRepository.findByTitleContainingAndActiveOrderByRegDateDesc(keyword, Boolean.TRUE);
        List<NoticeDto> noticeDtoList = new ArrayList<>();
        if(noticeList.isEmpty()) return noticeDtoList;
        for (Notice notice : noticeList) {
            NoticeDto noticeDto = toDto(notice);
            noticeDtoList.add(noticeDto);
        }
        return noticeDtoList;
    }

    public Notice findByNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new WrongBoothId());
    }

    public NoticeDto toDto(Notice notice) {
        return modelMapper.map(notice, NoticeDto.class);
    }

}
