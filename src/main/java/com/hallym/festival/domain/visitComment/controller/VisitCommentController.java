package com.hallym.festival.domain.visitComment.controller;

import com.hallym.festival.domain.comment.dto.CommentPasswordDto;
import com.hallym.festival.domain.visitComment.service.VisitCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/visitComment")
@RestController
public class VisitCommentController {
    private final VisitCommentService visitCommentService;

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id, @RequestBody
    CommentPasswordDto password) {
        return visitCommentService.delete(id, password);
    }
}
