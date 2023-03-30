package com.hallym.festival.domain.commentreport.service;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import com.hallym.festival.domain.commentreport.entity.CommentReport;
import com.hallym.festival.domain.commentreport.repository.CommentReportRepository;
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
public class CommentReportServiceImpl implements CommentReportService {

    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;

    public String reportCno(Long cno, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> commentCookie = findCommentCookie(request, cno);
        if (commentCookie.isPresent()) { //쿠키가 있을경우 return
            return "already reported";
        }
        //쿠키가 없을경우 추가.
        else {
            Optional<Comment> byId = commentRepository.findById(cno);
            if (byId.isEmpty()) {
                return "does not exist comment";
            }
            Comment comment = byId.get();
            Long reportCount = commentReportRepository.countByComment(comment);
            if (reportCount >= 2) {
                comment.setIs_deleted(Boolean.TRUE);
                return "report success and comment is deleted";
            }
            else {
                CommentReport commentReport = createCommentCookie(comment);
                Cookie keyCookie = new Cookie("cno"+cno.toString(), commentReport.getCookieKey());
                keyCookie.setMaxAge(14*60*60*24);
                keyCookie.setPath("/");
                response.addCookie(keyCookie);
                return "report success";
            }
        }
    }

    private Optional<Cookie> findCommentCookie(HttpServletRequest request, Long cno) {
        Cookie[] userCookies = request.getCookies();
        if (userCookies == null) {
            return Optional.empty();
        }
        for (Cookie userCookie : userCookies) {
            if (userCookie.getName().equals("cno"+cno.toString())) {
                return Optional.of(userCookie); //null값이 안들어가게
            }
        }
        return Optional.empty();
    }

    private CommentReport createCommentCookie(Comment comment) {
        String newCookieKey = createCookieKey();
        CommentReport commentReport = CommentReport.builder().comment(comment).cookieKey(newCookieKey).build();
        commentReportRepository.save(commentReport);
        return commentReport;
    }

    private String createCookieKey(){
        while (true) {
            String cookieKey = createRandomString();
            Optional<CommentReport> report = commentReportRepository.findByCookieKey(cookieKey);
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
