package com.hallym.festival.domain.booth.repository;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hallym.festival.domain.booth.service.boothSearch.BoothSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Long>, BoothSearch {
    //EntityGraph를 이용해서 lazy 로딩이여도 한 번에 join 처리 후, select 실행
    @EntityGraph(attributePaths = {"imageSet"}) //imageSet 속성을 같이 로딩
    @Query("select b from Booth b where b.bno =:bno")
    Optional<Booth> findByIdWithImages(@Param("bno") Long bno);

    Optional<Booth> findByWriter(String writer);

    /**
     * ------------------------백오피스 관련 JPQL-------------------------------------------------------
     */
    @Query("select b from Booth b " +
            "where b.is_deleted = :is_deleted " +
            "order by size(b.likes) desc, b.bno desc ")
    Page<Booth> listTopLikeBooth(@Param("is_deleted") boolean is_deleted, Pageable pageable);

    // 부스별 댓글 개수 내림차순
    // (삭제된 댓글 제외 - left join으로 booth별 comments 가져오고 group by 와 order by 를 이용해 댓글 개수별 내림차순 정렬)
    @Query("select b from Booth b " +
            "left join b.comments c " +
            "where b.is_deleted = :is_deleted_b and (c.is_deleted = :is_deleted_c or c.cno is null) " +
            "group by b " +
            "order by coalesce(sum(case when c.is_deleted = false then 1 else 0 end), 0) desc," +
            "b.bno desc ")
    Page<Booth> listTopCommentBooth(@Param("is_deleted_c") Boolean is_deleted_c,
                                    @Param("is_deleted_b") boolean is_deleted_b,   Pageable pageable);

    //부스별 댓글 신고수의 합 내림차순 (삭제된 댓글 포함)
    @Query(value =
            "select b from Booth b " +
            "left join b.comments c " +
            "left join c.commentReportList r " +
            "group by b.bno " +
            "order by count(r) desc, b.bno desc ")
    Page<Booth> listTopReportCountBooth(Pageable pageable);
}
