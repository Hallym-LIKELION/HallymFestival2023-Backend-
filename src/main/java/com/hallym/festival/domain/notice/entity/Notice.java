package com.hallym.festival.domain.notice.entity;

import com.hallym.festival.domain.notice.dto.NoticeResponseDto;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;


@Builder
@Table(name = "notice")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private boolean is_deleted;

    public NoticeResponseDto toDto() {
        NoticeResponseDto build = NoticeResponseDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .is_deleted(is_deleted)
                .build();
        return build;
    }
}
