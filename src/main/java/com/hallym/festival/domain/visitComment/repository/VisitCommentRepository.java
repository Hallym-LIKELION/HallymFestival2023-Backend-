package com.hallym.festival.domain.visitComment.repository;

import com.hallym.festival.domain.visitComment.entity.VisitComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitCommentRepository extends JpaRepository<VisitComment, Long> {
    List<VisitComment> findByBooth_IdAndActiveOrderByCreatedDateTimeDesc(Long id, Boolean active);
}
