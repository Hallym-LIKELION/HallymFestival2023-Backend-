package com.hallym.festival.domain.likes.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesResponseTopDTO {
    private Long bno;
    private String booth_title;
    private int like_cnt;
}
