package com.hallym.festival.domain.visitcommentreport.repository;

import com.hallym.festival.domain.visitcomment.entity.VisitComment;
import com.hallym.festival.domain.visitcommentreport.entity.VisitCommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitCommentReportRepository extends JpaRepository<VisitCommentReport, Long> {
    Optional<VisitCommentReport> findByCookieKey(String CookieKey);
    Long countByVisitComment(VisitComment visitComment);
}
