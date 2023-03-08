package com.hallym.festival.domain.comment;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hallym.festival.domain.booth.Booth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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


