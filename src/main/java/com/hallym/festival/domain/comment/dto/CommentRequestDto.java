package com.hallym.festival.domain.comment.dto;

import com.hallym.festival.domain.booth.entity.Booth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private String ip;
    private String content;
    private Boolean active;
    private Booth booth;
}
