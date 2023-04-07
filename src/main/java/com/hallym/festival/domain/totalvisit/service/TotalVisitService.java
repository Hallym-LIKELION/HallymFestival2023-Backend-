package com.hallym.festival.domain.totalvisit.service;

import com.hallym.festival.domain.totalvisit.dto.TotalVisitCountDTO;
import com.hallym.festival.domain.totalvisit.entity.TotalVisit;

import java.util.List;

public interface TotalVisitService {

    void visit(TotalVisit dto);
    List<TotalVisitCountDTO> visitList();
}
