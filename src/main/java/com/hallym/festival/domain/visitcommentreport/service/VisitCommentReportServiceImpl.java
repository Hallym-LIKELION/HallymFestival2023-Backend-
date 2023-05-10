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
import java.util.List;
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
                // 방명록 신고 entity의 ip가 해당 방명록에서 중복이 아니라면
                if (checkDuplicateIpByVisitComment(visitComment, request)) {
                    VisitCommentReport visitCommentReport = createVisitCommentCookie(visitComment, request);
                    Cookie keyCookie = new Cookie("vno"+vno.toString(), visitCommentReport.getCookieKey());
                    keyCookie.setMaxAge(14*60*60*24);
                    keyCookie.setPath("/");
                    response.addCookie(keyCookie);
                    return "report success";
                } //중복 ip가 있으면
                return "duplicate ip";
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

    private Boolean checkDuplicateIpByVisitComment(VisitComment visitComment, HttpServletRequest request) {
        String remoteAddr = getRemoteAddr(request);

        //해당 방명록에 같은 ip로 좋아요 한 적 있으면 쿠키 생성 못하도록
        List<VisitCommentReport> visitCommentReports = visitComment.getVisitCommentReports();
        for (VisitCommentReport visitCommentReport : visitCommentReports) {
            //ip가 null 값이 아닐 때
            if (visitCommentReport.getIp() != null) {
                //해당 ip가 이미 있으면
                if (visitCommentReport.getIp().equals(remoteAddr)) {
                    return false;
                }
            }
        }

        return true;
    }

    private VisitCommentReport createVisitCommentCookie(VisitComment visitComment, HttpServletRequest request) {
        String newCookieKey = createCookieKey();
        VisitCommentReport visitCommentReport = VisitCommentReport.builder()
                .cookieKey(newCookieKey)
                .ip(getRemoteAddr(request))
                .build();
        
        visitCommentReport.setVisitComment(visitComment); //연관관계 참조
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
