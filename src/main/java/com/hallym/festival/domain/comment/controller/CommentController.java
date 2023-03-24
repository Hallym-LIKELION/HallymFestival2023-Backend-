package com.hallym.festival.domain.comment.controller;

import com.hallym.festival.domain.comment.dto.CommentPasswordDto;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("{bno}")
    public List<CommentResponseDto> getCommentList(@PathVariable(name = "bno") Long boothId) throws Exception {
        return commentService.getAll(boothId);
    }

    @PostMapping("{bno}")
    public Map<String, String> createComment
            (@PathVariable(name="bno") Long boothId,
             @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        commentService.create(boothId, commentRequestDto, request);
        return Map.of("result", "create success");
    }

    @DeleteMapping("{cno}")
    public Map<String, String> deleteComment(@PathVariable(name = "cno") Long commentId, @RequestBody CommentPasswordDto pwd) {
        String result = commentService.delete(commentId, pwd);
        return Map.of("result", result);
    }

}


