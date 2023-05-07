package com.hallym.festival.domain.totalvisit.service;

import com.hallym.festival.domain.totalvisit.dto.TotalVisitCountResponseDTO;
import com.hallym.festival.domain.totalvisit.entity.TotalVisit;

import java.util.List;

public interface TotalVisitService {

    void visit(TotalVisit dto);
    List<TotalVisitCountResponseDTO> visitList();
}
