package com.hallym.festival.domain.visitcomment.controller;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentPasswordDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentReportedResponseDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentRequestDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentResponseDTO;
import com.hallym.festival.domain.visitcomment.service.VisitCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/visitcomment")
@RequiredArgsConstructor
public class VisitCommentController {

    private final VisitCommentService visitCommentService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<VisitCommentResponseDTO> getVisitCommentList(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<VisitCommentResponseDTO> responseDTO = visitCommentService.getList(pageRequestDTO);
        return responseDTO;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createVisitComment(@RequestBody VisitCommentRequestDTO visitCommentRequestDto,
                                                  HttpServletRequest request) {
        String result = visitCommentService.create(visitCommentRequestDto, request);
        return Map.of("result", result);
    }

    @DeleteMapping("{vno}")
    public Map<String, String> deleteVisitComment(@PathVariable(name = "vno") Long vno,
                                             @RequestBody VisitCommentPasswordDTO pwd) {
        String result = visitCommentService.delete(vno, pwd);
        return Map.of("result", result);
    }

    @DeleteMapping("/force/{vno}")
    public Map<String, String> forceDeleteVisitComment(@PathVariable(name = "vno") Long vno) {
        String result = visitCommentService.forceDelete(vno);
        return Map.of("result", result);
    }

    @GetMapping("/reported")
    public PageResponseDTO<VisitCommentReportedResponseDTO> getReportedCommentList(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<VisitCommentReportedResponseDTO> responseDTO = visitCommentService.getReportedList(pageRequestDTO);
        return responseDTO;
    }

}
