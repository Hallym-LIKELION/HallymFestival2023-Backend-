package com.hallym.festival.domain.menu.dto;

import com.hallym.festival.domain.menu.entity.MenuSell;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {
    private Long mno;
    private String name;
    private Long price;
    private MenuSell menuSell;
}
