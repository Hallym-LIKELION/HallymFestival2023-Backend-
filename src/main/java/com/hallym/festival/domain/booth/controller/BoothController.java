package com.hallym.festival.domain.booth.controller;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.service.BoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booths")
@RequiredArgsConstructor
public class BoothController {

    private final BoothService boothService;

    @GetMapping("/list")
    public List<Booth> getAllBooths() {
        return boothService.getAllBooths();
    }
}
