package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MenuResponseDto;


import java.util.List;

public interface MenuService {
    MenuResponseDto create (Long bno, MenuRequestDto MenuRequestDto);

    List<MenuResponseDto> getAll(Long bno) throws Exception;

    MenuResponseDto modify(Long mno, MenuRequestDto MenuRequestDto);

    String delete(Long mno);

    String modifySoldOut(Long mno);
}
