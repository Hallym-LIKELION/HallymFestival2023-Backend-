package com.hallym.festival.domain.visitcomment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentResponseDto {
    private Long id;

    private String writer;

    private String content;

    private LocalDateTime createdDateTime;

}
