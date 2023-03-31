package com.hallym.festival.domain.visitcomment.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentPasswordDto;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentReportedResponseDto;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentRequestDto;
import com.hallym.festival.domain.visitcomment.dto.VisitCommentResponseDto;
import com.hallym.festival.domain.visitcomment.entity.VisitComment;
import com.hallym.festival.domain.visitcomment.repository.VisitCommentRepository;
import com.hallym.festival.global.security.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VisitCommentServiceImpl implements VisitCommentService{

    private final VisitCommentRepository visitCommentRepository;
    private final ModelMapper modelMapper;
    private final Encrypt encrypt;

    @Override
    public String create(VisitCommentRequestDto visitCommentRequestDto, HttpServletRequest request) {
        visitCommentRequestDto.setIp(getRemoteAddr(request));
        visitCommentRequestDto.setPassword(encrypt.getEncrypt(visitCommentRequestDto.getPassword()));
        visitCommentRequestDto.setIs_deleted(Boolean.FALSE);
        VisitComment visitComment = modelMapper.map(visitCommentRequestDto, VisitComment.class);
        visitCommentRepository.save(visitComment);
        return "create success";
    }

    @Override
    public String delete(Long vno, VisitCommentPasswordDto pwdDto) {
        Optional<VisitComment> byId = visitCommentRepository.findById(vno);
        if (byId.isEmpty()) {
            return "null visitcomment";
        }
        VisitComment visitComment = byId.get();
        if (visitComment.getPassword().equals(getEncpwd(pwdDto.getPassword()))) {
            visitComment.setIs_deleted(Boolean.TRUE);
            return "delete success";
        }
        else {
            return "wrong password";
        }
    }

    @Override
    public PageResponseDTO<VisitCommentResponseDto> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0:
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("vno").descending());

        Page<VisitComment> result = visitCommentRepository.list(Boolean.FALSE, pageable);
        List<VisitCommentResponseDto> dtoList = result.getContent()
                .stream()
                .map(v -> modelMapper.map(v, VisitCommentResponseDto.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<VisitCommentResponseDto>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<VisitCommentReportedResponseDto> getReportedList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0:
                        pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize());

        Page<VisitComment> result = visitCommentRepository.listReported(Boolean.FALSE, pageable);
        List<VisitCommentReportedResponseDto> dtoList = result.getContent()
                .stream()
                .map(this::visitCommentToVisitCommentReportedResponseDto)
                .collect(Collectors.toList());

        return PageResponseDTO.<VisitCommentReportedResponseDto>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    private VisitCommentReportedResponseDto visitCommentToVisitCommentReportedResponseDto(VisitComment visitComment) {
        return VisitCommentReportedResponseDto.builder()
                .vno(visitComment.getVno())
                .content(visitComment.getContent())
                .ip(visitComment.getIp())
                .report_cnt(visitComment.getVisitCommentReports().size())
                .build();
    }

    private String getEncpwd(String password) {
        return this.encrypt.getEncrypt(password);
    }

    //Extract Ip using HttpServletRequest
    private static String getRemoteAddr(HttpServletRequest request) {

        String ip = null;

        ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_CLIENT_IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_X_FORWARDED_FOR");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("X-Real-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("X-RealIP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("REMOTE_ADDR");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();

        }

        return ip;

    }
}
