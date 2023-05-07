package com.hallym.festival.domain.booth.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.dto.BoothListAllDTO;
import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoothServiceImpl implements BoothService{

    private final BoothRepository boothRepository;

    @Override
    public PageResponseDTO<BoothListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoothListAllDTO> result = boothRepository.searchWithAll(types, keyword, pageable);

        return PageResponseDTO.<BoothListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    //게시글의 이미지와 댓글의 숫자까지 처리

    public Long register(BoothDTO boothDTO){ //등록

        Booth booth = dtoToEntity(boothDTO);

        log.info(booth);

        Long bno = boothRepository.save(booth).getBno();

        return bno;
    }

    @Override
    public BoothDTO getOne(Long bno) {

        Optional<Booth> result = boothRepository.findByIdWithImages(bno);

        Booth booth = result.orElseThrow();

        BoothDTO boothDTO = entityToDTO(booth);

        return boothDTO;
    }

    @Override
    public void modify(Long bno, BoothDTO boothDTO) { //수정

        Optional<Booth> result = boothRepository.findById(bno);

        Booth booth = result.orElseThrow();

        booth.change(boothDTO.getBooth_title(), boothDTO.getBooth_content(), boothDTO.getWriter(), boothDTO.getBooth_type(), boothDTO.getDayNight(), booth.getOpenDay());

        booth.clearImages();

        if(boothDTO.getFileNames() != null){
            for (String fileName : boothDTO.getFileNames()) {
                String[] arr = fileName.split("_");
                booth.addImage(arr[0], arr[1]);
            }
        }

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


}