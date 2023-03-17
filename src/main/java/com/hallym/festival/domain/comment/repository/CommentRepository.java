package com.hallym.festival.domain.comment.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
