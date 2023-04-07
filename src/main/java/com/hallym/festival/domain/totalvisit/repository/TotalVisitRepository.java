package com.hallym.festival.domain.totalvisit.repository;

import com.hallym.festival.domain.totalvisit.dto.TotalVisitCountDTO;
import com.hallym.festival.domain.totalvisit.entity.TotalVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TotalVisitRepository extends JpaRepository<TotalVisit, Long> {

    @Query(value =
        "SELECT " +
        " new com.hallym.festival.domain.totalvisit.dto.TotalVisitCountDTO(t.visitDate, count(t))" +
        "from TotalVisit t " +
        "GROUP BY t.visitDate order by t.visitDate desc")
    List<TotalVisitCountDTO> countByVisitDate();
}
