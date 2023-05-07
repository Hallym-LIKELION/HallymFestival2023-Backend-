package com.hallym.festival.domain.menu.controller;

import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MeunResponseDto;
import com.hallym.festival.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    @PreAuthorize("authentication.principal.username == #menuRequestDto.writer or hasRole('ROLE_ADMIN')")
    @PostMapping("/auth/{bno}")
    public Map<String, String> createMenu(@PathVariable(name = "bno") Long bno,
                                          @RequestBody MenuRequestDto menuRequestDto) {
        menuService.create(bno, menuRequestDto);
        return Map.of("result","create success");
    }

    @GetMapping("/{bno}")
    public List<MeunResponseDto> getMenuList(@PathVariable(name = "bno") Long bno) throws Exception {
        return menuService.getAll(bno);
    }

    @PreAuthorize("authentication.principal.username == #menuRequestDto.writer or hasRole('ROLE_ADMIN')")
    @PutMapping("/auth/{mno}")
    public Map<String, String> updateMenu(@RequestBody MenuRequestDto menuRequestDto,
                                          @PathVariable (name = "mno") Long mno) {
        menuService.modify(mno, menuRequestDto);
        return Map.of("result","update success");
    }

    @PreAuthorize("authentication.principal.username == #menuRequestDto.writer or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/auth/{mno}")
    public Map<String, String> deleteMenu(@PathVariable(name = "mno") Long mno, MenuRequestDto menuRequestDto) {
        String result = menuService.delete(mno);
        return Map.of("result", result);
    }

    @PreAuthorize("authentication.principal.username == #menuRequestDto.writer or hasRole('ROLE_ADMIN')")
    @PutMapping("/auth/sell/{mno}")
    public Map<String, String> modifySell(@PathVariable(name = "mno") Long mno, MenuRequestDto menuRequestDto) {
        String result = menuService.modifySoldOut(mno);
        return Map.of("result", result);
    }
}
