package com.hallym.festival.domain.booth.controller;

import com.hallym.festival.domain.booth.dto.*;
import com.hallym.festival.domain.booth.dto.upload.UploadResultDTO;
import com.hallym.festival.domain.booth.service.BoothService;
import com.hallym.festival.domain.comment.dto.CommentResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/booth")
@RequiredArgsConstructor
@Log4j2
public class BoothController {

    @Value("${com.hallym.festival.upload.path}")
    private String uploadPath;

    private final ModelMapper modelMapper;

    private final BoothService boothService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<BoothListAllDTO> list(PageRequestDTO pageRequestDTO) {

        PageResponseDTO<BoothListAllDTO> responseDTO = boothService.listWithAll(pageRequestDTO);

        log.info(responseDTO);

        return responseDTO;
    }

    @GetMapping("/{bno}")
    public BoothDTO read(@PathVariable("bno") Long bno) {

        BoothDTO boothDTO = boothService.readOne(bno);

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

    @PutMapping("/modify/{bno}")
    public Map<String, String> modify(@PathVariable("bno") Long bno, @Valid @RequestBody BoothDTO boothDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        log.info("booth modify post......." + boothDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            log.info("-----modify----알맞지 않은 입력 값입니다-----");

            return Map.of("result", "failed");
        }

        boothService.modify(bno, boothDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boothDTO.getBno());

        return Map.of("result", "modify success");
    }

    @PostMapping ("/{bno}")
    public Map<String, String> remove(@PathVariable(name = "bno") Long bno) {


        log.info("remove post.." +  bno);

        boothService.remove(bno);
        BoothDTO boothDTO = boothService.readOne(bno);

        //게시물이 삭제되었다면 첨부파일도 삭제
        log.info(boothDTO.getFileNames());
        List<String> fileNames = boothDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0 && boothDTO.is_deleted() != false){
            removeFiles(fileNames);
        }

        return Map.of("result","Delete Booth" + bno + " is success");
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