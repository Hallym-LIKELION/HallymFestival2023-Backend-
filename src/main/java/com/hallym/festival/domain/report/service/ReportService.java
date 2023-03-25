package com.hallym.festival.domain.report.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReportService {
    String report(Long commentId, HttpServletRequest request, HttpServletResponse response);
}
