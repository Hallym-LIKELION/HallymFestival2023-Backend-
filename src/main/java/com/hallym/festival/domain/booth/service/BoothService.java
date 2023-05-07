package com.hallym.festival.domain.booth.service;

import com.hallym.festival.domain.booth.dto.BoothDTO;
import com.hallym.festival.domain.booth.dto.BoothListAllDTO;
import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.dto.upload.UploadResultDTO;
import com.hallym.festival.domain.booth.entity.Booth;

import java.util.List;
import java.util.stream.Collectors;


public interface BoothService {

    Long register(BoothDTO boothDTO);

    BoothDTO getOne(Long bno);

    void modify(Long bno, BoothDTO boothDTO);

    void remove(Long bno);

    Long getMyBooth(String writer);

    //게시글의 이미지 포함
    PageResponseDTO<BoothListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

    default Booth dtoToEntity(BoothDTO boothDTO){ //DTO를 엔티티로 변환

        Booth booth = Booth.builder()
                .bno(boothDTO.getBno())
                .booth_title(boothDTO.getBooth_title())
                .booth_content(boothDTO.getBooth_content())
                .writer(boothDTO.getWriter())
                .booth_type(boothDTO.getBooth_type())
                .dayNight(boothDTO.getDayNight())
                .openDay(boothDTO.getOpenDay())
                .build();

        if(boothDTO.getFileNames() != null){
            boothDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                booth.addImage(arr[0], arr[1]);
            });
        }
        return booth;
    }

    default BoothDTO entityToDTO(Booth booth) { //엔티티를 DTO로 변환

        BoothDTO boothDTO = BoothDTO.builder()
                .bno(booth.getBno())
                .booth_title(booth.getBooth_title())
                .booth_content(booth.getBooth_content())
                .writer(booth.getWriter())
                .booth_type(booth.getBooth_type())
                .dayNight(booth.getDayNight())
                .openDay(booth.getOpenDay())
                .comment_cnt(booth.getComments().size())
                .like_cnt(booth.getLikes().size())
                .regDate(booth.getRegDate())
                .build();

        List<String> fileNames =
                booth.getImageSet().stream().sorted().map(boothImage ->
                        boothImage.getUuid()+"_"+boothImage.getFileName()).collect(Collectors.toList());

        boothDTO.setFileNames(fileNames);

        return boothDTO;
    }

}
