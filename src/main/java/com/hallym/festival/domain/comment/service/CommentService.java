package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import com.hallym.festival.global.exception.WrongBoothId;
import com.hallym.festival.global.exception.WrongCommentId;
import com.hallym.festival.global.exception.WrongIp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoothRepository boothRepository;

    public CommentResponseDto create(Long boothId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Optional<Booth> byId = boothRepository.findById(boothId);
        if (byId.isEmpty()) {
            throw new WrongBoothId();
        }
        Booth booth = byId.get();
        commentRequestDto.setBooth(booth);
        commentRequestDto.setIp(getRemoteAddr(request));
        commentRequestDto.setActive(Boolean.TRUE);
        Comment comment = requestDtoToEntity(commentRequestDto);
        Comment save = commentRepository.save(comment);
        return entityToResponseDto(save);
    }

    public HttpStatus delete(Long commentId, HttpServletRequest request) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isEmpty()) {
            throw new WrongCommentId();
        }

        Comment comment = byId.get();

        if (comment.getIp().equals(getRemoteAddr(request))) {
            comment.setActivte(Boolean.FALSE);
            return HttpStatus.OK;
        }
        else{
            throw new WrongIp();
        }

    }

    public List<CommentResponseDto> getAll(Long boothId) throws Exception {
        Optional<Booth> byId = boothRepository.findById(boothId);
        if (byId.isEmpty()) {
            throw new Exception();
        }
        List<Comment> comments = commentRepository.findByBooth_IdAndActiveOrderByRegDate(boothId, Boolean.TRUE);
        return getResponseDtoList(comments);
    }

    public Comment requestDtoToEntity(CommentRequestDto commentRequestDto) {
        return Comment.builder()
                .content(commentRequestDto.getContent())
                .active(commentRequestDto.getActive())
                .ip(commentRequestDto.getIp())
                .booth(commentRequestDto.getBooth())
                .build();
    }

    public CommentResponseDto entityToResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .regDate(comment.getRegDate())
                .build();
    }

    // 모든 댓글을 entityToResponseDto 함수적용하여 ResponseDto로 변환후 Return
    private List<CommentResponseDto> getResponseDtoList(List<Comment> all) {
        return all.stream().map(this::entityToResponseDto)
                .collect(Collectors.toList());
    }



    //Extract Ip using HttpServletRequest
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
