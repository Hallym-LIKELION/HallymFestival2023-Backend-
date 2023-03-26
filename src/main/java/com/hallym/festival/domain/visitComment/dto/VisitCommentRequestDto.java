package com.hallym.festival.domain.visitComment.dto;

import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentRequestDto extends BaseTimeEntity {

    private String ip;

    private Boolean active;

    private String visit_content;
}
