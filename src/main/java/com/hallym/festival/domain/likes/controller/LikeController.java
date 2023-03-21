package com.hallym.festival.domain.likes.controller;

import com.hallym.festival.domain.likes.dto.LikesResponseDto;
import com.hallym.festival.domain.likes.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("{bno}")
    public Map<String, String> likeCreate(@PathVariable Long bno, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> boothCookie = likeService.findBoothCookie(request, bno);
        if (boothCookie.isPresent()) { // 쿠키가 있는데 다시 좋아요 요청한경우 올바른 요청이아님.
            return Map.of("result", "already created");
        }
        LikesResponseDto likes = likeService.create(bno);
        Cookie keyCookie = new Cookie(bno.toString(), likes.getCookieKey());
        keyCookie.setMaxAge(14*60*60*24); // 2주일
        keyCookie.setPath("/");
        response.addCookie(keyCookie);
        return Map.of("result", "create success");
    }

    @DeleteMapping("{bno}")
    public Map<String, String> likeDelete(@PathVariable Long bno, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> boothCookie = likeService.findBoothCookie(request, bno);
        if (boothCookie.isPresent()) {
            Cookie userCookie = boothCookie.get();
            String CookieKey = userCookie.getValue();
            likeService.delete(bno, CookieKey);

            Cookie keyCookie = new Cookie(bno.toString(), null);
            keyCookie.setMaxAge(0);
            keyCookie.setPath("/");
            response.addCookie(keyCookie);
            return Map.of("result", "delete success");
        }
        else { // 쿠키 지워야되는데 쿠키가 없을때 (올바른 요청이 아님)
            return Map.of("result", "delete failed");
        }
    }
}
