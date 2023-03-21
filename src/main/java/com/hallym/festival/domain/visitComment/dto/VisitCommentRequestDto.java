package com.hallym.festival.domain.visitcomment.dto;

import com.hallym.festival.domain.booth.entity.Booth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentRequestDto {
    private String writer;

    private String password;

    private String ip;

    private Boolean active;

    private String content;

    private Booth booth;
}
