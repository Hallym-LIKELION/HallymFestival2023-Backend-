package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MenuResponseDto;
import com.hallym.festival.domain.menu.entity.Menu;
import com.hallym.festival.domain.menu.repository.MenuRepository;
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
        Menu newMenu = menuRequestDto.toEntity(menuRequestDto);
        newMenu.setActive(Boolean.TRUE);
        Menu menu = menuRepository.save(newMenu);
        return toDto(menu);
    }

    public List<MenuResponseDto> getAll(Long boothId) throws Exception {
        Optional<Booth> booth = findByBooth(boothId);
        List<Menu> menuList = menuRepository.findByBooth_BnoAndActiveOrderByRegDateDesc(boothId, Boolean.TRUE);
        return getMenuList(menuList);
    }

    @Transactional
    public MenuResponseDto update(Long id, MenuRequestDto menuRequestDto) {
        Menu menu = menuRepository.findById(id).orElseThrow(() ->
                new WrongBoothId());
        menu.updateMenu(dtoToEntity(menuRequestDto));
        menuRepository.save(menu);
        return toDto(menu);
    }

    @Transactional
    public String delete(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() ->
                new WrongBoothId());
        menu.setActive(Boolean.FALSE);
        return "delete success";
    }

    public Optional<Booth> findByBooth(Long id) {
        return Optional.ofNullable(boothRepository.findById(id).orElseThrow(() ->
                new WrongBoothId()));
    }

    public List<MenuResponseDto> getMenuList(List<Menu> all) {
        return all.stream().map(menu -> this.toDto(menu)).collect(Collectors.toList());
    }

    private Menu dtoToEntity(MenuRequestDto menuRequestDto) {
        return Menu.builder()
                .name(menuRequestDto.getName())
                .price(menuRequestDto.getPrice())
                .booth(menuRequestDto.getBooth())
                .build();
    }

    public MenuResponseDto toDto(Menu menu) {
        return modelMapper.map(menu, MenuResponseDto.class);
    }
}
