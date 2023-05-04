package com.hallym.festival.domain.booth.repository;

import com.hallym.festival.domain.booth.entity.Booth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hallym.festival.domain.booth.service.boothSearch.BoothSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Long>, BoothSearch {
    //EntityGraph를 이용해서 lazy 로딩이여도 한 번에 join 처리 후, select 실행
    @EntityGraph(attributePaths = {"imageSet"}) //imageSet 속성을 같이 로딩
    @Query("select b from Booth b where b.bno =:bno")
    Optional<Booth> findByIdWithImages(Long bno);

    /**
     * ------------------------백오피스 관련 JPQL-------------------------------------------------------
     */

    @Query("select b from Booth b order by size(b.likes) desc")
    Page<Booth> listTopLikeBooth(Pageable pageable);
    @Query("select b from Booth b order by size(b.comments) desc")
    Page<Booth> listTopCommentBooth(Pageable pageable);
    @Query(value =
            "select b from Booth b " +
            "left join b.comments c " +
            "left join c.commentReportList r " +
            "group by b.bno " +
            "order by sum(r.size) desc")
    Page<Booth> listTopReportCountBooth(Pageable pageable);
}