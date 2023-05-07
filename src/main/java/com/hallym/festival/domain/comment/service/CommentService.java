package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.comment.dto.*;
import com.hallym.festival.domain.commentreport.controller.CommentTopReportCountDTO;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    String create(Long boothId, CommentRequestDTO commentRequestDto, HttpServletRequest request);
    String delete(Long commentId, CommentPasswordDTO pwdDto);
    String forceDelete(Long commentId);
    PageResponseDTO<CommentResponseDTO> getListOfBooth(Long bno, PageRequestDTO pageRequestDTO);
    PageResponseDTO<CommentReportedResponseDTO> getReportedList(PageRequestDTO pageRequestDTO);
    PageResponseDTO<CommentTopCountDTO> getTopCountList(PageRequestDTO pageRequestDTO);
    PageResponseDTO<CommentTopReportCountDTO> getTopReportCountList(PageRequestDTO pageRequestDTO);
}
