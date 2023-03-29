package com.hallym.festival.domain.visitcommentreport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VisitCommentReportService {
    String reportVno(Long vno, HttpServletRequest request, HttpServletResponse response);
}
