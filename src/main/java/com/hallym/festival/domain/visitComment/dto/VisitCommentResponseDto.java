package com.hallym.festival.domain.visitComment.dto;

import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;



@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentResponseDto extends BaseTimeEntity {

    private Long vno;
    private String visit_content;
    private String ip;
    private Boolean active;
}
