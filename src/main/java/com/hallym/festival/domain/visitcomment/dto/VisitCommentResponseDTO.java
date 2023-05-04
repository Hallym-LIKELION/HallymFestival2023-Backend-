package com.hallym.festival.domain.visitcomment.dto;

import com.hallym.festival.domain.visitcomment.entity.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentResponseDTO {
    private Long vno;
    private String content;
    private String ip;
    private Color color;
}
