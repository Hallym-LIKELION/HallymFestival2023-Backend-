package com.hallym.festival.domain.comment.repository;

import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.booth.bno = :bno and c.is_deleted=:is_deleted")
    Page<Comment> listofBooth(@Param("bno") Long bno, @Param("is_deleted") Boolean is_deleted, Pageable pageable);
    void deleteByBooth_Bno(Long bno);

}
