package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MenuResponseDto;

import java.util.List;

public interface MenuService {
    MenuResponseDto create (Long id, MenuRequestDto menuRequestDto);

    List<MenuResponseDto> getAll(Long boothId) throws Exception;

    MenuResponseDto update(Long id, MenuRequestDto menuRequestDto);

    String delete(Long id);
}
