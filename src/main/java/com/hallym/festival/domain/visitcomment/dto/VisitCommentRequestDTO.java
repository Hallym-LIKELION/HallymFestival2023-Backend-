package com.hallym.festival.domain.visitcomment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentRequestDTO {
    private String ip;
    @NotNull
    private String password;
    private String content;
    private Boolean is_deleted;
}
