package com.hallym.festival.domain.booth.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hallym.festival.domain.booth.entity.BoothActive;
import com.hallym.festival.domain.booth.entity.BoothType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoothDTO {

    private Long bno;

    @NotEmpty(message = "부스 이름은 필수 입력 값입니다.")
    @Size(min = 3, max = 100)
    private String booth_title;

    @NotEmpty //NULL, 빈 문자열 불가
    private String booth_content;

    @NotEmpty
    private String writer;

    @NotNull
    private BoothType booth_type;

    private BoothActive booth_active;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") //JSON 처리 시 포맷팅 지정
    private LocalDateTime regDate;

    @JsonIgnore //JSON 변환에서 제외
    private LocalDateTime modDate;

}
