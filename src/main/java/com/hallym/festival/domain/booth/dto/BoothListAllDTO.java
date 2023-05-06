package com.hallym.festival.domain.booth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.entity.DayNight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoothListAllDTO {

    private Long bno;

    private String booth_title;

    private String booth_content;

    private String writer;

    private BoothType booth_type;

    private DayNight dayNight;

    private String openDay;

    private int comment_cnt; // response에서만

    private int like_cnt; // response에서만

    private List<BoothImageDTO> boothImages;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") //JSON 처리 시 포맷팅 지정
    private LocalDateTime regDate;
}
