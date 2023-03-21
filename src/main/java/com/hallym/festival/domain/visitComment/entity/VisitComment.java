package com.hallym.festival.domain.visitcomment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hallym.festival.domain.booth.entity.Booth;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visitComment")
@Builder
@Entity
public class VisitComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String writer;

    @NotNull
    private String password;

    @NotNull
    private String content;

    @NotNull
    private String ip;

    @NotNull
    private Boolean active;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setActivte(Boolean active){
        this.active = active;
    }
}
