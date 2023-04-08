package com.hallym.festival.domain.menu.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.menu.dto.MenuRequestDto;
import com.hallym.festival.domain.menu.dto.MeunResponseDto;
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
    public MeunResponseDto create(Long boothId, MenuRequestDto menuRequestDto) {
        Optional<Booth> booth = boothRepository.findById(boothId);
        menuRequestDto.setBooth(booth.get());
        Menu newMenu = modelMapper.map(menuRequestDto, Menu.class);
        menuRepository.save(newMenu);
        return modelMapper.map(newMenu, MeunResponseDto.class);
    }

    public List<MeunResponseDto> getAll(Long boothId) throws Exception {
        Optional<Booth> booth = boothRepository.findById(boothId);
        List<Menu> menuList = menuRepository.findByBooth_Bno(booth.get().getBno(), Boolean.FALSE);
        return getMenuList(menuList);
    }

    @Transactional
    public MeunResponseDto modify(Long id, MenuRequestDto menuRequestDto) {
        Menu menu = menuRepository.findById(id).orElseThrow(() ->
                new WrongBoothId());
        menu.updateMenu(modelMapper.map(menuRequestDto, Menu.class));
        menuRepository.save(menu);
        return modelMapper.map(menu, MeunResponseDto.class);
    }

    @Transactional
    public String delete(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() ->
                new WrongBoothId());
        menu.setIs_deleted(Boolean.TRUE);
        return "delete success";
    }

    public List<MeunResponseDto> getMenuList(List<Menu> all) {
        return all.stream().map(menu -> this.toDto(menu)).collect(Collectors.toList());
    }

    public MeunResponseDto toDto(Menu menu) {
        return modelMapper.map(menu, MeunResponseDto.class);
    }
}
