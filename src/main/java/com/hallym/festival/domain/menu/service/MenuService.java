package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.menu.dto.MenuDto;


import java.util.List;

public interface MenuService {
    MenuDto create (Long id, MenuDto menuDto);

    List<MenuDto> getAll(Long boothId) throws Exception;

    MenuDto update(Long id, MenuDto menuDto);

    String delete(Long id);
}
