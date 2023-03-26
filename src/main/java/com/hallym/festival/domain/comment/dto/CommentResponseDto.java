package com.hallym.festival.domain.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long cno;
    private String content;
    private String ip;
}
