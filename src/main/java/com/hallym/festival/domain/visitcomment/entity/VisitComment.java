package com.hallym.festival.domain.visitcomment.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hallym.festival.domain.commentreport.entity.CommentReport;
import com.hallym.festival.domain.visitcommentreport.entity.VisitCommentReport;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visitcomment")
@Builder
@Entity
public class VisitComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vno")
    private Long vno;

    @NotNull
    private String content;

    @NotNull
    private String password;

    @NotNull
    private String ip;

    @NotNull //삭제 여부
    private Boolean is_deleted;

    @Builder.Default
    @JsonManagedReference(value="visit") //부모클래스에 JsonManagedReference 붙여서 순환참조 방어.
    @OneToMany(mappedBy = "visitComment", fetch = FetchType.LAZY)
    private List<VisitCommentReport> visitCommentReports = new ArrayList<>();

    public void setIs_deleted(Boolean is_deleted){
        this.is_deleted = is_deleted;
    }

}



