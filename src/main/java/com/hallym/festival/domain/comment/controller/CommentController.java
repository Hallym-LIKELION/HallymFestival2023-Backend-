package com.hallym.festival.domain.comment.controller;

import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService ;

    @DeleteMapping("/{id}")
    public HttpStatus deleteComment(@PathVariable(name = "id") Long commentId, HttpServletRequest request) {
        HttpStatus status = commentService.delete(commentId, request);
        return status;
    }
}


