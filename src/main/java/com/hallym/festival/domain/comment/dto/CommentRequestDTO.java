package com.hallym.festival.domain.comment.dto;

import com.hallym.festival.domain.booth.entity.Booth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {
    private String ip;
    @NotNull
    private String password;
    private String content;
    private Boolean is_deleted;
    private Booth booth;
}
