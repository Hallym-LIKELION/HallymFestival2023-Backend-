package com.hallym.festival.domain.likes.controller;

import com.hallym.festival.domain.likes.service.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeServiceImpl likeServiceImpl;

    @GetMapping("{bno}")
    public Map<String, Integer> getLikeCnt(@PathVariable Long bno) {
        int cnt = likeServiceImpl.getCount(bno);
        return Map.of("result" , cnt);
    }

    @PostMapping("{bno}")
    public Map<String, String> like(@PathVariable Long bno, HttpServletRequest request, HttpServletResponse response) {
        String result = likeServiceImpl.likeTrigger(bno, request, response);
        return Map.of("result", result);
    }
}
