package com.hallym.festival.domain.notice.entity;

import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String content;
}
