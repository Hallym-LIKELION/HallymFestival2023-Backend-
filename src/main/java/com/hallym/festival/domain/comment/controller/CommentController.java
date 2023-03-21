package com.hallym.festival.domain.comment.controller;

import com.hallym.festival.domain.comment.dto.CommentPasswordDto;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @DeleteMapping("/{id}")
    public Map<String, String> deleteComment(@PathVariable(name = "id") Long commentId, @RequestBody CommentPasswordDto pwd) {
        String result = commentService.delete(commentId, pwd);

        return Map.of("result", result);
    }
}


