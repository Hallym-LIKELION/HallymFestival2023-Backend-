package com.hallym.festival.domain.report.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByCookieKey(String CookieKey);
    Long countByComment(Comment comment);
}
