package com.hallym.festival.domain.likes.service;

import com.hallym.festival.domain.likes.dto.LikesResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LikeService {
    String likeTrigger(Long bno, HttpServletRequest request, HttpServletResponse response);
    int getCount(Long boothId);

}
