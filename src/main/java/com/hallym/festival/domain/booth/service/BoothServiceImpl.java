package com.hallym.festival.domain.booth.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoothServiceImpl implements BoothService{

    private final BoothRepository boothRepository;

    private final ModelMapper modelMapper;

    public List<Booth> getList() {
        return boothRepository.findAll();
    }

    public void register(BoothDTO boothDTO){
        log.info(modelMapper);

        Booth booth = modelMapper.map(boothDTO, Booth.class);

        log.info(booth);

        boothRepository.save(booth);
    }
}