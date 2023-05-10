package com.hallym.festival.domain.commentreport.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hallym.festival.domain.comment.entity.Comment;
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
public class CommentReport extends BaseTimeRegDateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crno;

    @NotNull
    private String cookieKey;

    private String ip;

    //json backreferecne 2개 넣으면 오류난대서 value 지정하기.
    @JsonBackReference(value="comment") //부모클래스에 JsonBackReference 붙여서 순환참조 방어.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cno")
    private Comment comment;

    public void setComment(Comment comment) {
        if (this.comment != null) {
            this.comment.getCommentReportList().remove(this);
        }
        this.comment = comment;
        comment.getCommentReportList().add(this);
    }
}
