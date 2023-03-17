package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;

    public List<Comment> getAllBooths() {
        return commentRepository.findAll();
    }
}
