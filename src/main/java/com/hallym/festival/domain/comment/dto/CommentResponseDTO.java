package com.hallym.festival.domain.comment.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private Long cno;
    private String content;
    private String ip;
}
