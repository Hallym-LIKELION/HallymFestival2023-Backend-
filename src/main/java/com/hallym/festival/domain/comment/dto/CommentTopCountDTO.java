package com.hallym.festival.domain.comment.dto;

import com.hallym.festival.domain.booth.entity.BoothType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentTopCountDTO {
    private Long bno;
    private BoothType boothType;
    private String booth_title;
    private String booth_content;
    private String writer;
    private LocalDateTime regDate;
    private int comment_cnt;
}
