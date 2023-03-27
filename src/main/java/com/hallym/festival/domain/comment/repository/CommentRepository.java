package com.hallym.festival.domain.comment.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    @Query("select r from Comment r where r.booth.bno = :bno") //게시물 삭제 시 댓글들 삭제를 위한 쿼리
    List<Comment> findByBooth_BnoAndActiveOrderByRegDateDesc(Long bno, Boolean active);

    void deleteByBooth_Bno(Long bno);

}
