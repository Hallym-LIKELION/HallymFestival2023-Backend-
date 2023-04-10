package com.hallym.festival.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.commentreport.entity.CommentReport;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Builder
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cno")
    private Long cno;

    @NotNull
    private String content;

    @NotNull
    private String password;

    @NotNull
    private String ip;

    @NotNull //삭제 여부
    private Boolean is_deleted;

    @Builder.Default
    @JsonManagedReference(value="comment") // 부모클래스S
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<CommentReport> commentReportList = new ArrayList<>();

    @JsonBackReference // 자식클래스
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno", nullable = false)
    private Booth booth;

    public void setIs_deleted(Boolean is_deleted){
        this.is_deleted = is_deleted;
    }

    public void setBooth(Booth booth) {
        if (this.booth != null) { //기존에 부스가 존재한다면
            this.booth.getComments().remove(this); //관계를 끊는다.
        }
        this.booth = booth;
        booth.getComments().add(this);
    }
}


