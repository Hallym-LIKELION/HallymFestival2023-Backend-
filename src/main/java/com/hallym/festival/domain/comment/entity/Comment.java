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
    @Column(name = "comment_id")
    private Long cno;

    @NotNull
    private String content;

    @NotNull
    private String password;

    @NotNull
    private String ip;

    @NotNull //삭제 여부
    private Boolean is_deleted;

    @JsonManagedReference(value="comment") // 부모클래스S
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<CommentReport> commentReportList = new ArrayList<>();

    @JsonBackReference // 자식클래스
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public void setIs_deleted(Boolean is_deleted){
        this.is_deleted = is_deleted;
    }

}


