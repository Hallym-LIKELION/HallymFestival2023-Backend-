package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.comment.dto.CommentPasswordDto;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    void create(Long boothId, CommentRequestDto commentRequestDto, HttpServletRequest request);
    String delete(Long commentId, CommentPasswordDto pwdDto);
    PageResponseDTO<CommentResponseDto> getListofBooth(Long bno, PageRequestDTO pageRequestDTO);
}
