package com.hallym.festival.domain.booth.controller;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.service.BoothService;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/booth")
@RequiredArgsConstructor
public class BoothController {

    private final BoothService boothService;
    private final CommentService commentService;

    @GetMapping("/list")
    public List<Booth> getAllBooths() {
        return boothService.getAllBooths();
    }

    @PostMapping("/{id}/comments")
    public CommentResponseDto createComment
            (@PathVariable(name="id") Long boothId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) throws Exception {
        return commentService.create(boothId, commentRequestDto, request);
    }
}
