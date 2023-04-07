package com.hallym.festival.domain.comment.service;

import com.hallym.festival.domain.booth.dto.PageRequestDTO;
import com.hallym.festival.domain.booth.dto.PageResponseDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.comment.dto.CommentPasswordDTO;
import com.hallym.festival.domain.comment.dto.CommentReportedResponseDTO;
import com.hallym.festival.domain.comment.dto.CommentRequestDTO;
import com.hallym.festival.domain.comment.dto.CommentResponseDTO;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import com.hallym.festival.global.security.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public String create(Long bno, CommentRequestDTO commentRequestDto, HttpServletRequest request) {
        Optional<Booth> byId = boothRepository.findById(bno);
        if (byId.isEmpty()) {
            return "booth is null";
        }
        else {
            Booth booth = byId.get();
            commentRequestDto.setBooth(booth);
            commentRequestDto.setIp(getRemoteAddr(request));
            commentRequestDto.setIs_deleted(Boolean.FALSE);
            commentRequestDto.setPassword(encrypt.getEncrypt(commentRequestDto.getPassword()));
            Comment comment = modelMapper.map(commentRequestDto, Comment.class);
            commentRepository.save(comment);
            return "create success";
        }
    }

    public String delete(Long cno, CommentPasswordDTO pwdDto) {
        Optional<Comment> byId = commentRepository.findById(cno);
        if (byId.isEmpty()) {
            return "null comment";
        }
        Comment comment = byId.get();
        if (comment.getPassword().equals(getEncpwd(pwdDto.getPassword()))) {
            comment.setIs_deleted(Boolean.TRUE);
            return "delete success";
        }
        else{ // 비밀번호가 다를경우.
            return "wrong password";
        }
    }

    @Override
    public PageResponseDTO<CommentResponseDTO> getListofBooth(Long bno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0:
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("cno").descending());

        Page<Comment> result = commentRepository.listofBooth(bno, Boolean.FALSE, pageable);
        List<CommentResponseDTO> dtoList = result.getContent()
                .stream()
                .map(comment -> modelMapper.map(comment, CommentResponseDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<CommentResponseDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<CommentReportedResponseDTO> getReportedList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0:
                        pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize());

        Page<Comment> result = commentRepository.listReported(Boolean.FALSE, pageable);
        List<CommentReportedResponseDTO> dtoList = result.getContent()
                .stream()
                .map(this::commentToCommentReportedResponseDto)
                .collect(Collectors.toList());

        return PageResponseDTO.<CommentReportedResponseDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    private CommentReportedResponseDTO commentToCommentReportedResponseDto(Comment comment) {
        return CommentReportedResponseDTO.builder()
                .bno(comment.getBooth().getBno())
                .cno(comment.getCno())
                .booth_title(comment.getBooth().getBooth_title())
                .content(comment.getContent())
                .ip(comment.getIp())
                .regDate(comment.getRegDate())
                .report_cnt(comment.getCommentReportList().size())
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
