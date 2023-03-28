package com.hallym.festival.domain.visitComment.service;

import com.hallym.festival.domain.visitComment.entity.VisitComment;
import com.hallym.festival.domain.visitComment.dto.VisitCommentResponseDTO;
import com.hallym.festival.domain.visitComment.repository.VisitCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VisitCommentService {

    private final VisitCommentRepository visitCommentRepository;
    private final ModelMapper modelMapper;

    public List<VisitCommentResponseDTO> getAll() {
        List<VisitComment> visitComments = visitCommentRepository.findByActiveOrderByRegDateDesc(Boolean.TRUE);
        return getResponseDtoList(visitComments);
    }

    @Transactional
    public void create(com.hallym.festival.domain.visitComment.dto.VisitCommentRequestDTO visitCommentRequestDto, HttpServletRequest request) {

        visitCommentRequestDto.setIp(getRemoteAddr(request));
        visitCommentRequestDto.setActive(Boolean.TRUE);
        VisitComment visitComment = modelMapper.map(visitCommentRequestDto, VisitComment.class);
        visitCommentRepository.save(visitComment);
    }

    @Transactional
    public String delete(Long vno) {
        Optional<VisitComment> byId = visitCommentRepository.findById(vno);
        if (byId.isEmpty()) {
            return "empty VisitComment";
        }
        VisitComment visitComment = byId.get();
        visitComment.setActivte(Boolean.FALSE);
        return "delete success";
    }

    public VisitCommentResponseDTO entityToResponseDto(VisitComment visitComment) {
        return modelMapper.map(visitComment, VisitCommentResponseDTO.class);
    }

    private List<VisitCommentResponseDTO> getResponseDtoList(List<VisitComment> all) {
        return all.stream().map(visitComment -> this.entityToResponseDto(visitComment)) //this::entityToResponseDto 랑 같음
                .collect(Collectors.toList());
    }


    public static String getRemoteAddr(HttpServletRequest request) {
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
