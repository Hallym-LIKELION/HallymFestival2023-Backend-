package com.hallym.festival.domain.visitcomment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentResponseDto {
    private Long vno;
    private String content;
    private String ip;
}
