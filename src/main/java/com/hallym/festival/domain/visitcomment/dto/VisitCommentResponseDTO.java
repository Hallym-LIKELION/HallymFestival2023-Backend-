package com.hallym.festival.domain.visitcomment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hallym.festival.domain.visitcomment.entity.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentResponseDTO {
    private Long vno;
    private String content;
    private String ip;
    private Color color;
    private int report_cnt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") //JSON 처리 시 포맷팅 지정
    private LocalDateTime regDate;
}
