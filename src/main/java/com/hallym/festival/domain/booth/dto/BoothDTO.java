package com.hallym.festival.domain.booth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoothDTO {

    private Long id;

    @NotEmpty(message = "부스 이름은 필수 입력 값입니다.")
    @Size(min = 3, max = 100)
    private String booth_title;

    @NotEmpty //NULL, 빈 문자열 불가
    private String booth_content;

    @NotEmpty
    private String writer;

    @NotEmpty
    private String booth_type;

//    @NotEmpty
//    private List<Integer> dayOfWeek;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
