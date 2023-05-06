package com.hallym.festival.domain.booth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoothImageDTO {

    private String uuid;

    private String fileName;

    private int ord;
}
