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

    @GetMapping("/list")
    public List<Booth> getAllBooths() {
        return boothService.getList();
    }
    @PostMapping("register")
    public String registerPost(@Valid BoothDTO boothDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) { //검증에 문제가 있다면 입력 화면으로 리다이렉트
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() ); //잘못된 결과는 addFlashAttribute()로 전달
            return "redirect:/booths/register";
        }

        log.info(boothDTO);

        boothService.register(boothDTO);

        redirectAttributes.addFlashAttribute("result");

        return "redirect:/booths/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, Model model){

        BoothDTO boothDTO = boothService.getOne(bno);

        log.info(boothDTO);

        model.addAttribute("dto", boothDTO);

    }

    @PostMapping("/modify")
    public String modify( @Valid BoothDTO boothDTO ,
                          BindingResult bindingResult,
                          PageRequestDTO pageRequestDTO,
                          RedirectAttributes redirectAttributes){

        log.info("board modify post......." + boothDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

            redirectAttributes.addAttribute("bno", boothDTO.getBno());

            return "redirect:/board/modify?"+link;
        }

        boothService.modify(boothDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boothDTO.getBno());

        return "redirect:/board/read";
    }


    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + bno);

        boothService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/booth/list";
    }
}