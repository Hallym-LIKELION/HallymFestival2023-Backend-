package com.hallym.festival.domain.likes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") //JSON 처리 시 포맷팅 지정
    private LocalDateTime regDate;
    private int like_cnt;
}
