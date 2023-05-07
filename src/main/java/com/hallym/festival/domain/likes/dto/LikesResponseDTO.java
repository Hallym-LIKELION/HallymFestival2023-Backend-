package com.hallym.festival.domain.likes.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesResponseDTO {
    private Long bno;
    private String cookieKey;
}
