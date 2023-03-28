package com.hallym.festival.domain.notice.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByActive(Boolean active);
    List<Notice> findByTitleContainingAndActive(String keyword, Boolean active);


}
