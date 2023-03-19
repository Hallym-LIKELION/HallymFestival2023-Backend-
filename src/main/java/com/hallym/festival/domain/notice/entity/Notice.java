package com.hallym.festival.domain.notice.entity;

import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;


@Builder
@Table(name = "notice")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String content;
}
