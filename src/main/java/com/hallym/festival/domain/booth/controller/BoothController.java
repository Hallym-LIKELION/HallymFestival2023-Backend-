package com.hallym.festival.domain.booth.controller;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.service.BoothService;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.service.CommentService;
import com.hallym.festival.domain.likes.dto.LikesResponseDto;
import com.hallym.festival.domain.likes.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/booth")
@RequiredArgsConstructor
@Log4j2
public class BoothController {

    private final BoothService boothService;
    private final CommentService commentService;
    private final LikeService likeService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Booth> getAllBooths() {
        return boothService.getList();
    }

    @GetMapping("/{bno}")
    public BoothDTO read(@PathVariable("bno") Long bno){

        BoothDTO boothDTO = boothService.getOne(bno);

        log.info(boothDTO);

        return boothDTO;
    }

    @PostMapping("register")
    public Map<String, String> registerPost(@Valid @RequestBody BoothDTO boothDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) { //검증에 문제가 있다면 입력 화면으로 리다이렉트
            log.info("has errors.......");
            log.info("-----register----알맞지 않은 입력 값입니다-----");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() ); //잘못된 결과는 addFlashAttribute()로 전달
            return Map.of("result","failed");
        }

        log.info(boothDTO);

        boothService.register(boothDTO);

        redirectAttributes.addFlashAttribute("result");

        return Map.of("result","register success");
    }

    @PutMapping ("/modify/{bno}")
    public Map<String, String> modify( @PathVariable("bno") Long bno, @Valid @RequestBody BoothDTO boothDTO ,
                          BindingResult bindingResult,
                          PageRequestDTO pageRequestDTO,
                          RedirectAttributes redirectAttributes){

        log.info("board modify post......." + boothDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            log.info("-----modify----알맞지 않은 입력 값입니다-----");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

            return Map.of("result","failed");
        }

        boothService.modify(boothDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boothDTO.getBno());

        return Map.of("result","modify success");
    }


    @DeleteMapping ("/{bno}")
    public Map<String, String> remove(@PathVariable("bno") Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + bno);

        boothService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return Map.of("result","remove success");
    }

    @GetMapping("/{id}/comments")
    public List<CommentResponseDto> getCommentList(@PathVariable(name = "id") Long boothId) throws Exception {
        return commentService.getAll(boothId);
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