package com.hallym.festival.domain.comment.controller;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.comment.dto.*;
import com.hallym.festival.domain.comment.service.CommentService;
import com.hallym.festival.domain.commentreport.controller.CommentTopReportCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("{bno}")
    public PageResponseDTO<CommentResponseDTO> getCommentList
            (@PathVariable(name = "bno") Long bno,
             PageRequestDTO pageRequestDTO) {

        PageResponseDTO<CommentResponseDTO> responseDTO = commentService.getListOfBooth(bno, pageRequestDTO);
        return responseDTO;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/auth/top-count-list")
    public PageResponseDTO<CommentTopCountDTO> getTopCommentBoothList(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<CommentTopCountDTO> responseDTO = commentService.getTopCountList(pageRequestDTO);
        return responseDTO;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/auth/report-top-count-list")
    public PageResponseDTO<CommentTopReportCountDTO> getTopReportCountBoothList(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<CommentTopReportCountDTO> responseDTO = commentService.getTopReportCountList(pageRequestDTO);
        return responseDTO;
    }


    @PostMapping(value = "{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createComment
            (@PathVariable(name="bno") Long bno,
             @RequestBody CommentRequestDTO commentRequestDto, HttpServletRequest request){
        String result = commentService.create(bno, commentRequestDto, request);
        return Map.of("result", result);
    }

    @DeleteMapping("{cno}")
    public Map<String, String> deleteComment(@PathVariable(name = "cno") Long cno, @RequestBody CommentPasswordDTO pwd) {
        String result = commentService.delete(cno, pwd);
        return Map.of("result", result);
    }

    @PreAuthorize("authentication.principal.username == #commentRequestDTO.writer or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/auth/force/{cno}")
    public Map<String, String> forceDeleteComment(@PathVariable(name = "cno") Long cno, @RequestBody CommentRequestDTO commentRequestDTO) {
        String result = commentService.forceDelete(cno);
        return Map.of("result", result);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/auth/reported")
    public PageResponseDTO<CommentReportedResponseDTO> getReportedCommentList(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<CommentReportedResponseDTO> responseDTO = commentService.getReportedList(pageRequestDTO);
        return responseDTO;
    }
}


