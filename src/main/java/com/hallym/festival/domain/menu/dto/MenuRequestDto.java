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
    private boolean active;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public Menu toEntity(MenuRequestDto menuRequestDto) {
        return Menu.builder()
                .name(menuRequestDto.getName())
                .price(menuRequestDto.getPrice())
                .booth(menuRequestDto.getBooth())
                .build();
    }
}
