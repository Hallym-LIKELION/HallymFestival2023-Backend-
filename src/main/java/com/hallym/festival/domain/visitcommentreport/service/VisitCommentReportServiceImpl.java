package com.hallym.festival.domain.visitcommentreport.service;

import com.hallym.festival.domain.visitcomment.entity.VisitComment;
import com.hallym.festival.domain.visitcomment.repository.VisitCommentRepository;
import com.hallym.festival.domain.visitcommentreport.entity.VisitCommentReport;
import com.hallym.festival.domain.visitcommentreport.repository.VisitCommentReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class VisitCommentReportServiceImpl implements VisitCommentReportService{

    private final VisitCommentRepository visitCommentRepository;
    private final VisitCommentReportRepository visitCommentReportRepository;

    @Override
    public String reportVno(Long vno, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> visitCommentCookie = findVisitCommentCookie(request, vno);
        if (visitCommentCookie.isPresent()) {
            return "already repoted";
        }
        else {
            Optional<VisitComment> byId = visitCommentRepository.findById(vno);
            if (byId.isEmpty()) {
                return "does not exist visit comment";
            }
            VisitComment visitComment = byId.get();
            Long reportCount = visitCommentReportRepository.countByVisitComment(visitComment);
            if (reportCount >= 7) {
                visitComment.setIs_deleted(Boolean.TRUE);
                return "report success and visit comment is deleted";
            }
            else {
                VisitCommentReport visitCommentReport = createVisitCommentCookie(visitComment);
                Cookie keyCookie = new Cookie("vno"+vno.toString(), visitCommentReport.getCookieKey());
                keyCookie.setMaxAge(14*60*60*24);
                keyCookie.setPath("/");
                response.addCookie(keyCookie);
                return "report success";
            }
        }
    }

    private Optional<Cookie> findVisitCommentCookie(HttpServletRequest request, Long vno) {
        Cookie[] userCookies = request.getCookies();
        if (userCookies == null) {
            return Optional.empty();
        }
        for (Cookie userCookie : userCookies) {
            if (userCookie.getName().equals("vno"+vno.toString())) {
                return Optional.of(userCookie); //null값이 안들어가게
            }
        }
        return Optional.empty();
    }

    private VisitCommentReport createVisitCommentCookie(VisitComment visitComment) {
        String newCookieKey = createCookieKey();
        VisitCommentReport visitCommentReport = VisitCommentReport.builder().visitComment(visitComment).cookieKey(newCookieKey).build();
        visitCommentReportRepository.save(visitCommentReport);
        return visitCommentReport;
    }

    private String createCookieKey(){
        while (true) {
            String cookieKey = createRandomString();
            Optional<VisitCommentReport> report = visitCommentReportRepository.findByCookieKey(cookieKey);
            if (report.isEmpty()){
                return cookieKey;
            }
        }
    }

    private String createRandomString(){
        int targetStringLength = 8;
        Random random = new Random();
        return random.ints(97, 123)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
