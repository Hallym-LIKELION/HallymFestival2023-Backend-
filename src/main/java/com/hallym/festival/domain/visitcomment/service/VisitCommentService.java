package com.hallym.festival.domain.visitcomment.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentPasswordDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentReportedResponseDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentRequestDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface VisitCommentService {
    String create(VisitCommentRequestDTO visitCommentRequestDto, HttpServletRequest request);
    String delete(Long vno, VisitCommentPasswordDTO pwdDto);
    String forceDelete(Long vno);
    PageResponseDTO<VisitCommentResponseDTO> getList(PageRequestDTO pageRequestDTO);
    PageResponseDTO<VisitCommentReportedResponseDTO> getReportedList(PageRequestDTO pageRequestDTO);
}
