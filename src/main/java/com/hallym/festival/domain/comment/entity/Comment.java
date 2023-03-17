package com.hallym.festival.domain.comment.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hallym.festival.domain.booth.entity.Booth;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id ;

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


