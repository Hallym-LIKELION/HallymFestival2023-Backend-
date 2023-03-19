package com.hallym.festival.domain.booth.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.entity.Booth;

import java.util.List;

public interface BoothService {

    void register(BoothDTO boothDTO);

    List<Booth> getList();
}
