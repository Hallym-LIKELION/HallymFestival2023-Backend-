package com.hallym.festival.repository;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class CommentRepositoryTests {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoothRepository boothRepository;

    @Test
    public void commentInsert() throws Exception{
        MockHttpServletRequest request = new MockHttpServletRequest();

        Long bno = 1L;

        Optional<Booth> target = boothRepository.findById(bno);

        Booth booth = target.orElseThrow(); //첫 번째 게시물


        IntStream.rangeClosed(1,3).forEach(i -> {
            Comment comment = Comment.builder()
                    .content("또 올게요")
                    .ip(getRemoteAddr(request))
                    .active(true)
                    .booth(booth)
                    .build();

            commentRepository.save(comment);
        });
    }

    @Test
    public void CommentSearchTest() {
        Long boothId = 1L;
        Optional<Booth> target = boothRepository.findById(boothId);
        if(target.isPresent()) {

            List<Comment> CommentList = commentRepository.findByBooth_BnoAndActiveOrderByRegDate(boothId, Boolean.TRUE);

            log.info("comment length => " + CommentList.size());
        }
    }

    //extract ip address
    public static String getRemoteAddr(HttpServletRequest request) {

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
