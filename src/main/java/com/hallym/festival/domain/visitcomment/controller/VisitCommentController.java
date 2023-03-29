package com.hallym.festival.domain.visitcomment.controller;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentPasswordDto;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentRequestDto;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentResponseDto;
import com.hallym.festival.domain.visitcomment.service.VisitCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/visitcomment")
@RequiredArgsConstructor
public class VisitCommentController {

    private final VisitCommentServiceImpl visitCommentService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<VisitCommentResponseDto> getVisitCommentList(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<VisitCommentResponseDto> responseDTO = visitCommentService.getList(pageRequestDTO);
        return responseDTO;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createVisitComment(@RequestBody VisitCommentRequestDto visitCommentRequestDto,
                                                  HttpServletRequest request) {
        String result = visitCommentService.create(visitCommentRequestDto, request);
        return Map.of("result", result);
    }

    @DeleteMapping("{vno}")
    public Map<String, String> deleteComment(@PathVariable(name = "vno") Long vno,
                                             @RequestBody VisitCommentPasswordDto pwd) {
        String result = visitCommentService.delete(vno, pwd);
        return Map.of("result", result);
    }

}
