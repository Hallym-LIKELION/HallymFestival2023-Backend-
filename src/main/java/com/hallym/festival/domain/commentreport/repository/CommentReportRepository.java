package com.hallym.festival.domain.commentreport.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.commentreport.entity.CommentReport;
import com.hallym.festival.domain.visitcomment.entity.VisitComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    Optional<CommentReport> findByCookieKey(String CookieKey);
    Long countByComment(Comment comment);
}
