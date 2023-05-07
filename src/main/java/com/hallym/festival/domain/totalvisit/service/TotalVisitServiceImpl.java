package com.hallym.festival.domain.totalvisit.service;

import com.hallym.festival.domain.totalvisit.dto.TotalVisitCountResponseDTO;
import com.hallym.festival.domain.totalvisit.entity.TotalVisit;
import com.hallym.festival.domain.totalvisit.repository.TotalVisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TotalVisitServiceImpl implements TotalVisitService {

    private final TotalVisitRepository totalVisitRepository;

    @Override
    public void visit(TotalVisit totalVisit) {
        totalVisitRepository.save(totalVisit);
    }

    @Override
    public List<TotalVisitCountResponseDTO> visitList() {
        List<TotalVisitCountResponseDTO> dtoList = totalVisitRepository.countByVisitDate();
        return dtoList;
    }
}
