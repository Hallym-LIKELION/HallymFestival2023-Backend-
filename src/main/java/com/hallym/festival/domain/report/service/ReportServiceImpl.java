package com.hallym.festival.domain.report.service;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import com.hallym.festival.domain.report.entity.Report;
import com.hallym.festival.domain.report.repository.ReportRepository;
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
public class ReportServiceImpl implements  ReportService{

    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;

    public String report(Long commentId, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> commentCookie = findCommentCookie(request, commentId);
        if (commentCookie.isPresent()) { //쿠키가 있을경우 return
            return "already reported";
        }
        //쿠키가 없을경우 추가.
        else {
            Optional<Comment> byId = commentRepository.findById(commentId);
            if (byId.isEmpty()) {
                throw new RuntimeException();
            }
            Comment comment = byId.get();
            Long reportCount = reportRepository.countByComment(comment);
            if (reportCount >= 2) {
                comment.setActivte(Boolean.FALSE);
                return "report success and comment deleted";
            }
            else {
                Report report = createCookie(comment);
                Cookie keyCookie = new Cookie(commentId.toString(), report.getCookieKey());
                keyCookie.setMaxAge(14*60*60*24);
                keyCookie.setPath("/");
                response.addCookie(keyCookie);
                return "report success";
            }
        }
    }

    private Optional<Cookie> findCommentCookie(HttpServletRequest request, Long commentId) {
        Cookie[] userCookies = request.getCookies();
        if (userCookies == null) {
            return Optional.empty();
        }
        for (Cookie userCookie : userCookies) {
            if (userCookie.getName().equals(commentId.toString())) {
                return Optional.of(userCookie); //null값이 안들어가게
            }
        }
        return Optional.empty();
    }

    private Report createCookie(Comment comment) {
        String newCookieKey = createCookieKey();
        Report report = Report.builder().comment(comment).cookieKey(newCookieKey).build();
        reportRepository.save(report);
        return report;
    }

    private String createCookieKey(){
        while (true) {
            String cookieKey = createRandomString();
            Optional<Report> report = reportRepository.findByCookieKey(cookieKey);
            if (report.isEmpty()){
                return cookieKey;
            }
        }
    }

    private String createRandomString(){
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(97, 123)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
