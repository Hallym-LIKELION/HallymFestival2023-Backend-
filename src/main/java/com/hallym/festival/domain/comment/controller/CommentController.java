package com.hallym.festival.domain.comment.controller;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.comment.dto.CommentPasswordDTO;
import com.hallym.festival.domain.comment.dto.CommentReportedResponseDTO;
import com.hallym.festival.domain.comment.dto.CommentRequestDTO;
import com.hallym.festival.domain.comment.dto.CommentResponseDTO;
import com.hallym.festival.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("{bno}")
    public PageResponseDTO<CommentResponseDTO> getCommentList
            (@PathVariable(name = "bno") Long bno,
             PageRequestDTO pageRequestDTO) {

        PageResponseDTO<CommentResponseDTO> responseDTO = commentService.getListofBooth(bno, pageRequestDTO);
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

    @GetMapping("/reported")
    public PageResponseDTO<CommentReportedResponseDTO> getReportedCommentList(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<CommentReportedResponseDTO> responseDTO = commentService.getReportedList(pageRequestDTO);
        return responseDTO;
    }

}


