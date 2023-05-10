package com.hallym.festival.domain.visitcommentreport.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hallym.festival.domain.visitcomment.entity.VisitComment;
import com.hallym.festival.global.baseEntity.BaseTimeRegDateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class VisitCommentReport extends BaseTimeRegDateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vcrno")
    private Long vcrno;

    @NotNull
    private String cookieKey;

    private String ip;

    //json backreferecne 2개 넣으면 오류난대서 value 지정하기.
    @JsonBackReference(value="visitcomment") //부모클래스에 JsonBackReference 붙여서 순환참조 방어.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vno")
    private VisitComment visitComment;

    public void setVisitComment(VisitComment visitComment) {
        if (this.visitComment != null) {
            this.visitComment.getVisitCommentReports().remove(this);
        }
        this.visitComment = visitComment;
        visitComment.getVisitCommentReports().add(this);
    }
}
