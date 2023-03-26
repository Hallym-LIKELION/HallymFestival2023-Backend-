package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.comment.dto.CommentPasswordDto;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {

    void create(Long boothId, CommentRequestDto commentRequestDto, HttpServletRequest request);
    String delete(Long commentId, CommentPasswordDto pwdDto);
    List<CommentResponseDto> getAll(Long boothId) throws Exception;
}
