package com.hallym.festival.domain.booth.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.entity.Booth;

import java.util.List;

public interface BoothService {

    Long register(BoothDTO boothDTO);

    BoothDTO getOne(Long bno);

    PageResponseDTO<BoothDTO> list(PageRequestDTO pageRequestDTO);

    void modify(BoothDTO boothDTO);

    void remove(Long bno);
}
