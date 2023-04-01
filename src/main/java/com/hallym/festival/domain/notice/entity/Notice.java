package com.hallym.festival.domain.notice.entity;

import com.hallym.festival.domain.notice.dto.NoticeDto;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;



@Table(name = "notice")
@Entity
@Builder
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

    private boolean active;

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void updateNotice(Notice notice) {
        this.title = notice.title;
        this.content = notice.content;
    }

}
