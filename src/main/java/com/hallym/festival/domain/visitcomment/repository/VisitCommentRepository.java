package com.hallym.festival.domain.visitcomment.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.visitcomment.entity.VisitComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitCommentRepository extends JpaRepository<VisitComment, Long> {
    @Query("select c from VisitComment c where  c.is_deleted=:is_deleted")
    Page<VisitComment> list(@Param("is_deleted") Boolean is_deleted, Pageable pageable);
}
