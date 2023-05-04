package com.hallym.festival.domain.likes.dto;

import com.hallym.festival.domain.booth.entity.BoothType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesResponseTopDTO {
    private Long bno;
    private BoothType boothType;
    private String booth_title;
    private String booth_content;
    private String writer;
    private LocalDateTime regDate;
    private int like_cnt;
}
