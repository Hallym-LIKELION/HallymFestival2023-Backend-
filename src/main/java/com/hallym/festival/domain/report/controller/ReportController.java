package com.hallym.festival.domain.report.controller;

import com.hallym.festival.domain.report.service.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceImpl reportService;

    @PostMapping("{cno}")
    public Map<String, String> reportComment(
            @PathVariable(name = "cno") Long commentId,
            HttpServletRequest request,
            HttpServletResponse response) {
        String result = reportService.report(commentId, request, response);
        return Map.of("result", result);
    }

}
