package com.hallym.festival.domain.booth.controller;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.service.BoothService;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.service.CommentService;
import com.hallym.festival.domain.likes.dto.LikesResponseDto;
import com.hallym.festival.domain.likes.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/booth")
@RequiredArgsConstructor
public class BoothController {

    private final BoothService boothService;
    private final CommentService commentService;
    private final LikeService likeService;

    @GetMapping("/list")
    public List<Booth> getAllBooths() {
        return boothService.getAllBooths();
    }

    @PostMapping("/{id}/comments")
    public CommentResponseDto createComment
            (@PathVariable(name="id") Long boothId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) throws Exception {
        return commentService.create(boothId, commentRequestDto, request);
    }

    @PostMapping("/{id}/likes")
    public LikesResponseDto likeCreate(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> boothCookie = likeService.findBoothCookie(request, id);
        if (boothCookie.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요 누름");
        }
        LikesResponseDto likes = likeService.create(id);
        Cookie keyCookie = new Cookie(id.toString(), likes.getCookieKey());
        keyCookie.setMaxAge(14*60*60*24); // 2주일
        keyCookie.setPath("/");
        response.addCookie(keyCookie);
        return likes;
    }

    @DeleteMapping("/{id}/likes")
    public HttpStatus likeDelete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> boothCookie = likeService.findBoothCookie(request, id);
        if (boothCookie.isPresent()) {
            Cookie userCookie = boothCookie.get();
            String CookieKey = userCookie.getValue();
            likeService.delete(id, CookieKey);

            Cookie keyCookie = new Cookie(id.toString(), null);
            keyCookie.setMaxAge(0);
            keyCookie.setPath("/");
            response.addCookie(keyCookie);
        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }
}
