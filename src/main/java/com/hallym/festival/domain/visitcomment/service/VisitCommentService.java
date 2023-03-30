package com.hallym.festival.domain.visitcomment.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentPasswordDto;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentRequestDto;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface VisitCommentService {
    String create(VisitCommentRequestDto visitCommentRequestDto, HttpServletRequest request);
    String delete(Long vno, VisitCommentPasswordDto pwdDto);
    PageResponseDTO<VisitCommentResponseDto> getList(PageRequestDTO pageRequestDTO);
}
