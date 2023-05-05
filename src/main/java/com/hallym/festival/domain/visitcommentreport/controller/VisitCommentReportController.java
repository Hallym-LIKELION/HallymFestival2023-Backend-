package com.hallym.festival.domain.visitcommentreport.controller;


import com.hallym.festival.domain.visitcommentreport.service.VisitCommentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/report/visitcomment")
@RequiredArgsConstructor
public class VisitCommentReportController {
    
    private final VisitCommentReportService reportService;

    @PostMapping("{vno}")
    public Map<String, String> reportComment(
            @PathVariable(name = "vno") Long vno,
            HttpServletRequest request,
            HttpServletResponse response) {
        String result = reportService.reportVno(vno, request, response);
        return Map.of("result", result);
    }
}
