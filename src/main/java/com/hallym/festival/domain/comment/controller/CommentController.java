package com.hallym.festival.domain.comment.controller;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

        private final CommentService commentService ;

        @GetMapping("/list")
        public List<Comment> getAllComments() {
            return commentService.getAllBooths();
        }
    }


