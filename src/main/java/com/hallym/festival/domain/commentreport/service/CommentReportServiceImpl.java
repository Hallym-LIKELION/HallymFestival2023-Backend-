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
import java.util.List;
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
            if (reportCount >= 7) {
                comment.setIs_deleted(Boolean.TRUE);
                return "report success and comment is deleted";
            }
            else {
                // 댓글 신고 entity의 ip가 해당 댓글에서 중복이 아니라면
                if (checkDuplicateIpByComment(comment, request)) {
                    CommentReport commentReport = createCommentCookie(comment, request);
                    Cookie keyCookie = new Cookie("cno"+cno.toString(), commentReport.getCookieKey());
                    keyCookie.setMaxAge(14*60*60*24);
                    keyCookie.setPath("/");
                    response.addCookie(keyCookie);
                    return "report success";
                } //중복 ip가 있으면
                else {
                    return "duplicate ip";
                }
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

    private Boolean checkDuplicateIpByComment(Comment comment, HttpServletRequest request) {
        String remoteAddr = getRemoteAddr(request);

        //해당 댓글에 같은 ip로 좋아요 한 적 있으면 쿠키 생성 못하도록
        List<CommentReport> commentReportList = comment.getCommentReportList();
        for (CommentReport commentReport : commentReportList) {
            //ip가 null 값이 아닐 때
            if (commentReport.getIp() != null) {
                //해당 ip가 이미 있으면
                if (commentReport.getIp().equals(remoteAddr)) {
                    return false;
                }
            }
        }

        return true;
    }

    private CommentReport createCommentCookie(Comment comment, HttpServletRequest request) {
        String newCookieKey = createCookieKey();
        
        CommentReport commentReport = CommentReport.builder()
                .cookieKey(newCookieKey)
                .ip(getRemoteAddr(request))
                .build();
        
        commentReport.setComment(comment); //연관관계 참조
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

    private static String getRemoteAddr(HttpServletRequest request) {

        String ip = null;

        ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_CLIENT_IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_X_FORWARDED_FOR");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("X-Real-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("X-RealIP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("REMOTE_ADDR");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();

        }

        return ip;

    }
}
