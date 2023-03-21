package com.hallym.festival.domain.comment.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CommentPasswordDto {
    private String password;
}
