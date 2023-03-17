package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;

import java.util.List;

public class CommentService {

    private CommentRepository commentRepository;

    public List<Comment> getAllBooths() {
        return commentRepository.findAll();
    }
}
