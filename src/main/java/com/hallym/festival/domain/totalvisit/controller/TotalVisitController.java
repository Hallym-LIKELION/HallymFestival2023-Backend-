package com.hallym.festival.domain.totalvisit.controller;

import com.hallym.festival.domain.totalvisit.dto.TotalVisitCountDTO;
import com.hallym.festival.domain.totalvisit.entity.TotalVisit;
import com.hallym.festival.domain.totalvisit.service.TotalVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TotalVisitController {

    private final TotalVisitService totalVisitService;

    @GetMapping()
    public void visit(TotalVisit dto) {
        totalVisitService.visit(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/auth/visit-list")
    public List<TotalVisitCountDTO> visitList() {
        return totalVisitService.visitList();
    }
}
