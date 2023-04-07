package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.comment.dto.CommentPasswordDTO;
import com.hallym.festival.domain.comment.dto.CommentReportedResponseDTO;
import com.hallym.festival.domain.comment.dto.CommentRequestDTO;
import com.hallym.festival.domain.comment.dto.CommentResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    String create(Long boothId, CommentRequestDTO commentRequestDto, HttpServletRequest request);
    String delete(Long commentId, CommentPasswordDTO pwdDto);
    PageResponseDTO<CommentResponseDTO> getListofBooth(Long bno, PageRequestDTO pageRequestDTO);
    PageResponseDTO<CommentReportedResponseDTO> getReportedList(PageRequestDTO pageRequestDTO);
}
