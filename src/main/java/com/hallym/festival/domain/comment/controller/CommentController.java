package com.hallym.festival.domain.comment.controller;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.comment.dto.CommentPasswordDto;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentServiceImpl;

    @GetMapping("{bno}")
    public PageResponseDTO<CommentResponseDto> getCommentList
            (@PathVariable(name = "bno") Long boothId,
             PageRequestDTO pageRequestDTO) {

        PageResponseDTO<CommentResponseDto> responseDTO = commentServiceImpl.getListofBooth(boothId, pageRequestDTO);
        return responseDTO;
    }

    @PostMapping("{bno}")
    public Map<String, String> createComment
            (@PathVariable(name="bno") Long boothId,
             @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        commentServiceImpl.create(boothId, commentRequestDto, request);
        return Map.of("result", "create success");
    }

    @DeleteMapping("{cno}")
    public Map<String, String> deleteComment(@PathVariable(name = "cno") Long commentId, @RequestBody CommentPasswordDto pwd) {
        String result = commentServiceImpl.delete(commentId, pwd);
        return Map.of("result", result);
    }

}


