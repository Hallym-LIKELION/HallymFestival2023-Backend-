package com.hallym.festival.domain.booth.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public Long register(BoothDTO boothDTO){ //등록
        log.info(modelMapper);

        BoothType boothType = boothDTO.getBooth_type();

        log.info(boothType);

        Booth booth = modelMapper.map(boothDTO, Booth.class);


        log.info(booth);

        Long bno = boothRepository.save(booth).getId();

        return bno;
    }

    @Override
    public BoothDTO getOne(Long bno) {

        Optional<Booth> result = boothRepository.findById(bno);

        Booth booth = result.orElseThrow();

        BoothDTO boothDTO = modelMapper.map(booth, BoothDTO.class);

        return boothDTO;
    }

    @Override
    public void modify(BoothDTO boothDTO) { //수정

        Optional<Booth> result = boothRepository.findById(boothDTO.getBno());

        Booth booth = result.orElseThrow();

        booth.change(boothDTO.getBooth_title(), boothDTO.getBooth_content(), boothDTO.getWriter(), booth.getBooth_type(), booth.getActive());

        boothRepository.save(booth);
    }

    @Override
    public void remove(Long bno) { //삭제

        boothRepository.deleteById(bno);

    }
}