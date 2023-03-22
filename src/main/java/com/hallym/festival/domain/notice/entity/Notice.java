package com.hallym.festival.domain.notice.entity;

import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;


@Table(name = "notice")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String content;

    private boolean is_deleted;
}
