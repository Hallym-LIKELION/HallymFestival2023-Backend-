package com.hallym.festival.domain.visitComment.controller;


import com.hallym.festival.domain.visitComment.dto.VisitCommentRequestDto;
import com.hallym.festival.domain.visitComment.dto.VisitCommentResponseDto;
import com.hallym.festival.domain.visitComment.service.VisitCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/visitComment")
@RestController
public class VisitCommentController {

    private final VisitCommentService visitCommentService;

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VisitCommentResponseDto> getVisitCommentList() {
        return visitCommentService.getAll();
    }
    @PostMapping("/create")
    public Map<String, String> createVisitComment
            (@Valid @RequestBody VisitCommentRequestDto visitCommentRequestDto, HttpServletRequest request){
        visitCommentService.create(visitCommentRequestDto, request);
        return Map.of("result", "create success");
    }

    @DeleteMapping("{vno}")
    public Map<String, String> deleteVisitComment(@PathVariable Long vno) {
        String result = visitCommentService.delete(vno);
        return Map.of("result", result);
    }

}
