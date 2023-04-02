package com.hallym.festival.domain.menu.dto;

import com.hallym.festival.domain.booth.entity.Booth;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private Long id;
    private String name;
    private Long price;
    private Booth booth;
    private boolean is_deleted;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
