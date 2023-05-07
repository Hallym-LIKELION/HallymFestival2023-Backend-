package com.hallym.festival.domain.menu.dto;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.menu.entity.MenuSell;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequestDto {
    private Long mno;
    private String name;
    private Long price;
    private Boolean is_deleted;
    private Booth booth;
    private MenuSell menuSell;
    private String writer;
}
