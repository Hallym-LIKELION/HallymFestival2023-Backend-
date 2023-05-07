package com.hallym.festival.domain.totalvisit.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TotalVisitCountResponseDTO {
    @JsonFormat(pattern="yyyy-MM-dd") //JSON 처리 시 포맷팅 지정
    private LocalDate visitDate;
    private Long count;
}
