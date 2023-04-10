package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MeunResponseDto;


import java.util.List;

public interface MenuService {
    MeunResponseDto create (Long bno, MenuRequestDto MenuRequestDto);

    List<MeunResponseDto> getAll(Long bno) throws Exception;

    MeunResponseDto modify(Long mno, MenuRequestDto MenuRequestDto);

    String delete(Long mno);
}
