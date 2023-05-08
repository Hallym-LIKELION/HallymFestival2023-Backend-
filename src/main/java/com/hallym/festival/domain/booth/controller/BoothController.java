package com.hallym.festival.domain.booth.controller;

import com.hallym.festival.domain.booth.dto.*;
import com.hallym.festival.domain.booth.service.BoothService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/booth")
@RequiredArgsConstructor
@Log4j2
public class BoothController {

    @Value("${com.hallym.festival.upload.path}")
    private String uploadPath;

    private final BoothService boothService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<BoothListAllDTO> list(PageRequestDTO pageRequestDTO) {

        PageResponseDTO<BoothListAllDTO> responseDTO = boothService.listWithAll(pageRequestDTO);

        log.info(responseDTO);

        return responseDTO;
    }

    @GetMapping("/{bno}")
    public BoothDTO read(@PathVariable("bno") Long bno) {

        BoothDTO boothDTO = boothService.getOne(bno);

        log.info(boothDTO);

        return boothDTO;
    }

    @PostMapping("register")
    public Map<String, String> registerPost(@Valid @RequestBody BoothDTO boothDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) { //검증에 문제가 있다면 입력 화면으로 리다이렉트
            log.info("has errors.......");
            log.info("-----register----알맞지 않은 입력 값입니다-----");
            return Map.of("result", "failed");
        }

        log.info(boothDTO);

        boothService.register(boothDTO);

        return Map.of("result", "register success");
    }

    @PreAuthorize("authentication.principal.username == #boothDTO.writer or hasRole('ROLE_ADMIN')")
    @PutMapping("/auth/modify/{bno}")
    public Map<String, String> modify(@PathVariable("bno") Long bno, @Valid @RequestBody BoothDTO boothDTO,
                                      BindingResult bindingResult) {

        log.info("booth modify post......." + boothDTO);

        if (bindingResult.hasErrors()) {
            log.info(bindingResult.toString());
            log.info("has errors.......");
            log.info("-----modify----알맞지 않은 입력 값입니다-----");

            return Map.of("result", "failed");
        }

        boothService.modify(bno, boothDTO);

        return Map.of("result", "modify success");
    }

    @PreAuthorize("authentication.principal.username == #boothDTO.writer or hasRole('ROLE_ADMIN')")
    @PostMapping ("/auth/{bno}")
    public Map<String, String> remove(@PathVariable(name = "bno") Long bno, @RequestBody BoothDTO boothDTO) {

        log.info("remove post.." +  bno);

        boothService.remove(bno);

        BoothDTO findDTO = boothService.getOne(bno);

        //게시물이 삭제되었다면 첨부파일도 삭제
        log.info(findDTO.getFileNames());
        List<String> fileNames = findDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0 && findDTO.is_deleted() != false){
            removeFiles(fileNames);
        }

        return Map.of("result","Delete Booth" + bno + " is success");
    }

    @PreAuthorize("authentication.principal.username == #boothDTO.writer or hasRole('ROLE_ADMIN')")
    @PostMapping("/auth/mybooth")
    public Long getMyBooth(@RequestBody BoothDTO boothDTO) {
        Long bno = boothService.getMyBooth(boothDTO.getWriter());
        return bno;
    }

    public void removeFiles(List<String> files) { //파일 삭제 메소드

        for (String fileName : files) {

            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();


            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                //섬네일이 존재한다면
                if (contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                    thumbnailFile.delete();
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }//end for
    }

}