package com.hallym.festival.domain.menu.dto;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.menu.entity.Menu;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequestDto {
    private Long id;
    private String name;
    private Long price;
    private Booth booth;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public Menu toEntity() {
        return Menu.builder()
                .name(name)
                .price(price)
                .booth(booth)
                .build();
    }
}
