package com.hallym.festival.domain.notice.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("select n from Notice n where n.is_deleted=:is_deleted order by n.regDate desc")
    Page<Notice> findAllList(@Param("is_deleted") Boolean is_deleted, Pageable pageable);
    List<Notice> findByTitleContaining(String keyword);

    @Query("select n from Notice n where n.title LIKE %:title% and n.is_deleted=:is_deleted order by n.regDate desc")
    Page<Notice> SearchList(@Param("title") String title,@Param("is_deleted") Boolean is_deleted, Pageable pageable);



}
