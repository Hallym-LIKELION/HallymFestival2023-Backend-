package com.hallym.festival.domain.totalvisit.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TotalVisitCountDTO {
    private LocalDate visitDate;
    private Long count;
}
