package com.hallym.festival.domain.menu.controller;

import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MeunResponseDto;
import com.hallym.festival.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/{bno}")
    public Map<String, String> createMenu(@PathVariable(name = "bno") Long bno,
                                          @RequestBody MenuRequestDto menuRequestDto) {
        menuService.create(bno, menuRequestDto);
        return Map.of("result","create success");
    }

    @GetMapping("/{bno}")
    public List<MeunResponseDto> getMenuList(@PathVariable(name = "bno") Long bno) throws Exception {
        return menuService.getAll(bno);
    }

    @PutMapping("/{mno}")
    public Map<String, String> updateMenu(@RequestBody MenuRequestDto menuRequestDto,
                                          @PathVariable (name = "mno") Long mno) {
        menuService.modify(mno, menuRequestDto);
        return Map.of("result","update success");
    }

    @DeleteMapping("/{mno}")
    public Map<String, String> deleteMenu(@PathVariable(name = "mno") Long mno) {
        String result = menuService.delete(mno);
        return Map.of("result", result);
    }
}
