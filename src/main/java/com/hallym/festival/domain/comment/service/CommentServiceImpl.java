package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.comment.dto.CommentPasswordDto;
import com.hallym.festival.domain.comment.dto.CommentRequestDto;
import com.hallym.festival.domain.comment.dto.CommentResponseDto;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import com.hallym.festival.global.exception.WrongBoothId;
import com.hallym.festival.global.security.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoothRepository boothRepository;
    private final ModelMapper modelMapper;
    private final Encrypt encrypt;

    public void create(Long boothId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Optional<Booth> byId = boothRepository.findById(boothId);
        if (byId.isEmpty()) {
            throw new WrongBoothId();
        }
        Booth booth = byId.get();
        commentRequestDto.setBooth(booth);
        commentRequestDto.setIp(getRemoteAddr(request));
        commentRequestDto.setActive(Boolean.TRUE);
        commentRequestDto.setPassword(encrypt.getEncrypt(commentRequestDto.getPassword()));
        Comment comment = modelMapper.map(commentRequestDto, Comment.class);
        commentRepository.save(comment);
    }

    public String delete(Long commentId, CommentPasswordDto pwdDto) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isEmpty()) {
            return "empty Booth";
        }
        Comment comment = byId.get();
        if (comment.getPassword().equals(getEncpwd(pwdDto.getPassword()))) {
            comment.setActivte(Boolean.FALSE);
            return "delete success";
        }
        else{ // 비밀번호가 다를경우.
            return "wrong password";
        }
    }

    public List<CommentResponseDto> getAll(Long boothId) throws Exception {
        Optional<Booth> byId = boothRepository.findById(boothId);
        if (byId.isEmpty()) {
            throw new Exception();
        }
        List<Comment> comments = commentRepository.findByBooth_BnoAndActiveOrderByRegDateDesc(boothId, Boolean.TRUE);
        return getResponseDtoList(comments);
    }

    private String getEncpwd(String password) {
        return this.encrypt.getEncrypt(password);
    }

    private CommentResponseDto entityToResponseDto(Comment comment) {
        return modelMapper.map(comment, CommentResponseDto.class);
    }

    // 모든 댓글을 entityToResponseDto 함수적용하여 ResponseDto로 변환후 Return
    private List<CommentResponseDto> getResponseDtoList(List<Comment> all) {
        return all.stream().map(comment -> this.entityToResponseDto(comment)) //this::entityToResponseDto 랑 같음
                .collect(Collectors.toList());
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