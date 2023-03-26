package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MenuResponseDto;
import com.hallym.festival.domain.menu.entity.Menu;
import com.hallym.festival.domain.menu.repository.MenuRepository;
import com.hallym.festival.domain.notice.entity.Notice;
import com.hallym.festival.global.exception.WrongBoothId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final BoothRepository boothRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public MenuResponseDto create(Long boothId, MenuRequestDto menuRequestDto) {
        Optional<Booth> booth = findByBooth(boothId);
        menuRequestDto.setBooth(booth.get());
        Menu newMenu = menuRequestDto.toEntity();
        newMenu.setActive(Boolean.TRUE);
        Menu menu = menuRepository.save(newMenu);
        return toDto(menu);
    }

    public List<MenuResponseDto> getAll(Long boothId) throws Exception {
        Optional<Booth> booth = findByBooth(boothId);
        List<Menu> menuList = menuRepository.findByBooth_BnoAndActiveOrderByRegDateDesc(boothId, Boolean.TRUE);
        return getMenuList(menuList);
    }

    public Optional<Booth> findByBooth(Long id) {
        return Optional.ofNullable(boothRepository.findById(id).orElseThrow(() ->
                new WrongBoothId()));
    }

    public List<MenuResponseDto> getMenuList(List<Menu> all) {
        return all.stream().map(menu -> this.toDto(menu)).collect(Collectors.toList());
    }

    public MenuResponseDto toDto(Menu menu) {
        return modelMapper.map(menu, MenuResponseDto.class);
    }
}
