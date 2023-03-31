package com.hallym.festival.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentReportedResponseDto {
    private Long bno;
    private Long cno;
    private String booth_title;
    private String content;
    private String ip;
    private int report_cnt;
}
