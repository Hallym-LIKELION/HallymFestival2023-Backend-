package com.hallym.festival.domain.visitComment.entity;


import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visitComment")
@Builder
@Entity
public class VisitComment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vno;

    @NotNull
    private String visit_content;

    @NotNull
    private String ip;

    @NotNull
    private Boolean active;

    public void setActivte(Boolean active){
        this.active = active;
    }
}
