package com.hallym.festival.domain.booth.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoothServiceImpl implements BoothService{

    private final BoothRepository boothRepository;
    private final ModelMapper modelMapper;

    public PageResponseDTO<BoothDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Booth> result = boothRepository.searchAll(types, keyword, pageable);

        List<BoothDTO> dtoList = result.getContent().stream()
                .map(booth -> boothToboothDTO(booth)).collect(Collectors.toList());


        return PageResponseDTO.<BoothDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }

    public Long register(BoothDTO boothDTO){ //등록
        log.info(modelMapper);

        BoothType boothType = boothDTO.getBooth_type();

        log.info(boothType);

        Booth booth = modelMapper.map(boothDTO, Booth.class);


        log.info(booth);

        Long bno = boothRepository.save(booth).getBno();

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

        booth.change(boothDTO.getBooth_title(), boothDTO.getBooth_content(), boothDTO.getWriter(), boothDTO.getBooth_type());

        boothRepository.save(booth);
    }

    @Override
    public void remove(Long bno) { //삭제

        Optional<Booth> byId = boothRepository.findById(bno);

        Booth booth = byId.get();

        log.info("---선택한 부스 정보------");
        log.info(booth);

        booth.setIs_deleted(Boolean.TRUE);

        log.info("---삭제된 부스 번호------" + booth.getBno());
    }

    public BoothDTO boothToboothDTO(Booth booth) {
        return BoothDTO.builder()
                    .bno(booth.getBno())
                    .booth_title(booth.getBooth_title())
                    .booth_content(booth.getBooth_content())
                    .booth_type(booth.getBooth_type())
                    .writer(booth.getWriter())
                    .comment_cnt(booth.getComments().size())
                    .like_cnt(booth.getLikes().size())
                    .regDate(booth.getRegDate())
                    .build();
    }

}