package com.hallym.festival.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.report.entity.Report;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    @Builder.Default //reportList 인스턴스 생성 시 Report 값으로 초기화
    private List<Report> reportList = new ArrayList<Report>();

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public void setIs_deleted(Boolean is_deleted){
        this.is_deleted = is_deleted;
    }

}


