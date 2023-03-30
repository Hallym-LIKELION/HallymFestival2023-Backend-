package com.hallym.festival.domain.menu.controller;

import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MenuResponseDto;
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
    public Map<String, String> createMenu(@PathVariable(name = "bno") Long boothId,
                                          @RequestBody MenuRequestDto menuRequestDto) {
        menuService.create(boothId, menuRequestDto);
        return Map.of("result", "create success");
    }

    @GetMapping("/{bno}")
    public List<MenuResponseDto> getMenuList(@PathVariable(name = "bno") Long boothId) throws Exception {
        return menuService.getAll(boothId);
    }

    @PutMapping("/{id}")
    public Map<String, String> updateMenu(@RequestBody MenuRequestDto menuRequestDto,
                                          @PathVariable (name = "id") Long id) {
        menuService.update(id, menuRequestDto);
        return Map.of("result","update success");
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteMenu(@PathVariable(name = "id") Long id) {
        String result = menuService.delete(id);
        return Map.of("result", result);
    }
}
