package com.hallym.festival.domain.commentreport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommentReportService {
    String reportCno(Long cno, HttpServletRequest request, HttpServletResponse response);
}
