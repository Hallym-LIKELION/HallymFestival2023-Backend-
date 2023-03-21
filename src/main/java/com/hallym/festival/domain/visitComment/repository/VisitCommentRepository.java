package com.hallym.festival.domain.visitcomment.repository;


import com.hallym.festival.domain.visitcomment.entity.VisitComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitCommentRepository extends JpaRepository<VisitComment, Long> {
//    List<VisitComment> findByBooth_IdAndActiveOrderByCreatedDateTimeDesc(Long id, Boolean active);

}
