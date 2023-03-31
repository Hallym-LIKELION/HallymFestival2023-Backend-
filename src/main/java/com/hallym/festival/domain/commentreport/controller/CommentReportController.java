package com.hallym.festival.domain.commentreport.controller;

import com.hallym.festival.domain.commentreport.service.CommentReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/report/comment")
@RequiredArgsConstructor
public class CommentReportController {

    private final CommentReportServiceImpl reportService;

    @PostMapping("{cno}")
    public Map<String, String> reportComment(
            @PathVariable(name = "cno") Long cno,
            HttpServletRequest request,
            HttpServletResponse response) {
        String result = reportService.reportCno(cno, request, response);
        return Map.of("result", result);
    }
}
