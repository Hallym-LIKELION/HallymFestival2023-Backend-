package com.hallym.festival.domain.comment.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBooth_IdAndActiveOrderByRegDateDesc(Long id, Boolean active);
}
