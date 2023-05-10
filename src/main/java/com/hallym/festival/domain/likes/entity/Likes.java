package com.hallym.festival.domain.likes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.global.baseEntity.BaseTimeRegDateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Likes extends BaseTimeRegDateEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long lno;

    @NotNull
    private String cookieKey;

    @NotNull
    private String ip;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno")
    private Booth booth;

    public void setBooth(Booth booth) {
        if (this.booth != null) { //기존에 부스가 존재한다면
            this.booth.getComments().remove(this); //관계를 끊는다.
        }
        this.booth = booth;
        booth.getLikes().add(this);
    }
}
