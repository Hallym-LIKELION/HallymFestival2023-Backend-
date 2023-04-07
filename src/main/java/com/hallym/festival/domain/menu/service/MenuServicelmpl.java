package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.menu.dto.MenuDto;
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
public class MenuServicelmpl implements MenuService {
    private final MenuRepository menuRepository;
    private final BoothRepository boothRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public MenuDto create(Long boothId, MenuDto menuDto) {
        Optional<Booth> booth = findByBooth(boothId);
        menuDto.setBooth(booth.get());
        Menu newMenu = modelMapper.map(menuDto, Menu.class);
        menuRepository.save(newMenu);
        return modelMapper.map(newMenu, MenuDto.class);
    }

    public List<MenuDto> getAll(Long boothId) throws Exception {
        Optional<Booth> booth = findByBooth(boothId);
        List<Menu> menuList = menuRepository.findByBooth_Bno(boothId);
        return getMenuList(menuList);
    }

    @Transactional
    public MenuDto update(Long id, MenuDto menuDto) {
        Menu menu = menuRepository.findById(id).orElseThrow(() ->
                new WrongBoothId());
        menu.updateMenu(modelMapper.map(menuDto, Menu.class));
        menuRepository.save(menu);
        return modelMapper.map(menu, MenuDto.class);
    }

    @Transactional
    public String delete(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() ->
                new WrongBoothId());
        menu.setIs_deleted(Boolean.TRUE);
        return "delete success";
    }

    public Optional<Booth> findByBooth(Long id) {
        return Optional.ofNullable(boothRepository.findById(id).orElseThrow(() ->
                new WrongBoothId()));
    }

    public List<MenuDto> getMenuList(List<Menu> all) {
        return all.stream().map(menu -> this.toDto(menu)).collect(Collectors.toList());
    }

    public MenuDto toDto(Menu menu) {
        return modelMapper.map(menu,MenuDto.class);
    }
}
