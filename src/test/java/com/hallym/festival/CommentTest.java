package com.hallym.festival;

import com.hallym.festival.domain.booth.Booth;
import com.hallym.festival.domain.booth.BoothRepository;
import com.hallym.festival.domain.comment.Comment;
import com.hallym.festival.domain.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@Log4j2
public class CommentTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoothRepository boothRepository;

    @Test
    @Transactional
    public void insertTests() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();

        Booth booth = Booth.builder()
                .name("물풍선던기지")
                .description("대신 맞아드립니다.")
                .image("http://example.com/test11.png")
                .build();
        boothRepository.save(booth);

        Comment comment1 = Comment.builder()
                        .writer("박주영")
                        .password("1234")
                        .content("안녕하세요")
                        .ip(getRemoteAddr(request))
                        .active(true)
                        .booth(booth)
                                .build();

        Comment comment2 = Comment.builder()
                .writer("이동헌")
                .password("1234")
                .content("안녕못해요")
                .ip(getRemoteAddr(request))
                .active(true)
                .booth(booth)
                .build();

        log.info("------------------");
        log.info(booth);

        Comment savedComment1 = commentRepository.save(comment1);
        Comment savedComment2 = commentRepository.save(comment2);

        log.info("------Comment1------");
        log.info(savedComment1.getIp());
        log.info(savedComment1.getBooth());
        log.info(savedComment1.getContent());

        log.info("------Comment2------");

        log.info(savedComment2.getIp());
        log.info(savedComment2.getBooth());
        log.info(savedComment2.getContent());

    }

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
