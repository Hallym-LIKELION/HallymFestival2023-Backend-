package com.hallym.festival.domain.booth.controller;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.service.BoothService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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


}
