package com.hallym.festival.domain.notice.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.domain.notice.repository.NoticeRepository;
import com.hallym.festival.global.exception.WrongBoothId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class NoticeServicelmpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    public NoticeDto create(NoticeDto noticeDto) {  //공지사항 등록
        Notice notice = modelMapper.map(noticeDto, Notice.class); //toEntity
        noticeRepository.save(notice);
        return modelMapper.map(notice, NoticeDto.class); //toDto
    }

    public PageResponseDTO<NoticeDto> getNoticeListPage(PageRequestDTO pageRequestDTO) { //공지사항 페이징
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0:
                        pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize());

        Page<Notice> result = noticeRepository.findAllList(Boolean.FALSE, pageable);

        List<NoticeDto> dtoList = result.getContent()
                .stream()
                .map(notice -> modelMapper.map(notice, NoticeDto.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<NoticeDto>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    public NoticeDto getNotice(Long id) { //공지사항 상세 조회
        Notice notice = findByNotice(id);
        return modelMapper.map(notice, NoticeDto.class);
    }

    public String delete(Long id) { //공지사항 삭제
        Notice notice = findByNotice(id);
        notice.setIs_deleted(Boolean.TRUE);
        return "delete success";
    }

    public NoticeDto modify(Long id, NoticeDto noticeDto) { //공지사항 수정
        Notice notice = findByNotice(id);
        Notice newnotice = modelMapper.map(noticeDto, Notice.class);
        notice.updateNotice(newnotice);
        noticeRepository.save(notice);
        return  modelMapper.map(notice, NoticeDto.class);
    }

    public PageResponseDTO<NoticeDto> searchPage(String keyword, PageRequestDTO pageRequestDTO) { //검색 페이징
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0:
                        pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize());

        Page<Notice> result = noticeRepository.SearchList(keyword, Boolean.FALSE, pageable);

        List<NoticeDto> dtoList = result.getContent()
                .stream()
                .map(notice -> modelMapper.map(notice, NoticeDto.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<NoticeDto>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    public Notice findByNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new WrongBoothId());
    }

    public NoticeDto toDto(Notice notice) {
        return modelMapper.map(notice, NoticeDto.class);
    }

}
