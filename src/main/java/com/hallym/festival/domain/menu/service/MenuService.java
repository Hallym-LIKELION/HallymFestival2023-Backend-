package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MeunResponseDto;


import java.util.List;

public interface MenuService {
    MeunResponseDto create (Long id, MenuRequestDto MenuRequestDto);

    List<MeunResponseDto> getAll(Long boothId) throws Exception;

    MeunResponseDto modify(Long id, MenuRequestDto MenuRequestDto);

    String delete(Long id);
}
