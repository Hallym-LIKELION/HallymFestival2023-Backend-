package com.hallym.festival.domain.comment.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBooth_BnoAndActiveOrderByRegDateDesc(Long bno, Boolean active);

    void deleteByBooth_Bno(Long bno);

}
