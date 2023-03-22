package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor //의존성 주입 생성자 방법
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public List<NoticeDto> getNotices() { //전체 게시물 조회
        List<Notice> Notices = noticeRepository.findAll();
        List<NoticeDto> NoticeDtos = new ArrayList<>();
        Notices.forEach(s -> NoticeDtos.add(NoticeDto.toDto(s)));
        return NoticeDtos;
    }

    @Transactional
    public NoticeDto getNotice(Long id) { //한 개의 게시물 조회
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("notice id를 찾을 수가 없습니다.");
        });
        NoticeDto noticeDto = NoticeDto.toDto(notice);
        return noticeDto;
    };

    //admin 도메인이 추가되면 다시 수정
/*
    @Transactional
    public NoticeDto writeNotice(NoticeDto noticeDto, Admin admin) { //게시물 작성
        Notice notice = new Notice();
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setAdmin(admin);
        noticeRepository.save(notice);
        return NoticeDto.toDto(notice);
    }
*/

    @Transactional
    public NoticeDto updateNotice(Long id, NoticeDto noticeDto) { //게시물 수정
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("notice id를 찾을 수가 없습니다.");
        });
        notice.setTitle(notice.getTitle());
        notice.setContent(noticeDto.getContent());

        return NoticeDto.toDto(notice);
    }

    @Transactional
    public NoticeDto deleteNotice(Long id) { //게시물 삭제
        // findByid로 일치하는 게시물이 존재하는지 먼저 찾음
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("notice id를 찾을 수가 없습니다.");
        });
        // is_deleted를 false로 변경
        notice.set_deleted(false);
        // 다시 notice 객체를 돌려줌
        return NoticeDto.toDto(notice);
    }

    @Transactional
    public List<NoticeDto> serarch(String keyword) {
        List<NoticeDto> noticeList = null;
        try {
            noticeList = noticeRepository.findByTitle(keyword);
        } catch (IllegalArgumentException e) {
            System.out.println("검색결과가 없습니다.");
        }
        return noticeList;
    }
}
