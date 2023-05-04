package com.hallym.festival.repository;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import com.hallym.festival.global.security.Encrypt;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
@Transactional
public class CommentRepositoryTests {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BoothRepository boothRepository;
    @Autowired
    Encrypt encrypt;

    @Test // 1번부스가 있다고 가정하고 1번 부스에 30개의 댓글 입력.
    public void commentInsert() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        Long bno = 1L;

        Optional<Booth> target = boothRepository.findById(bno);

        Booth booth = target.orElseThrow(); //첫 번째 게시물


        IntStream.rangeClosed(1, 5).forEach(i -> {
            Comment comment = Comment.builder()
                    .content("또 올게요")
                    .ip(getRemoteAddr(request))
                    .is_deleted(Boolean.FALSE)
                    .password(getEncpwd("1234"))
                    .booth(booth)
                    .build();

            commentRepository.save(comment);
        });
    }

    @Test
    @DisplayName("부스 댓글 양방향 관계매핑 확인 -- 지연로딩 comment 조회시 booth 조회하지않음")
    @Rollback(value = false)
    public void testInsertBoothAndComment() {
        //give
        Booth booth = boothRepository.save(Booth.builder()
                .booth_title("부스명..." )
                .booth_content("동아리 소개..." )
                .booth_type(BoothType.푸드트럭)
                .writer("부스담당매니저")
                .build());

        Comment comment = Comment.builder()
                .content("댓글입니당")
                .password("1234")
                .ip("1234")
                .is_deleted(false)
                .build();

        Comment comment2 = Comment.builder()
                .content("댓글입니당")
                .password("1234")
                .ip("1234")
                .is_deleted(false)
                .build();

        comment.setBooth(booth);
        comment2.setBooth(booth);
        commentRepository.save(comment);
        commentRepository.save(comment2);

        //Then
        Assertions.assertThat(booth.getComments().size()).isEqualTo(2);
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

    private String getEncpwd(String password) {
        return this.encrypt.getEncrypt(password);
    }
}
