package com.hallym.festival.domain.booth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booths")
@RequiredArgsConstructor
@Log4j2
public class BoothController {

    private final BoothService boothService;

    @GetMapping("/list")
    public List<Booth> getAllBooths() {
        return boothService.getAllBooths();
    }
}
