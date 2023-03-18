package com.hallym.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.likes.entity.Likes;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Table(name = "booth")
@Entity
public class Booth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booth_id")
    private Long id;

    @Column(length = 30, nullable = false) //컬럼의 길이와 null허용여부
    private String booth_title;

    @Column(length = 500, nullable = false)
    private String booth_content;

    @Column(length = 30, nullable = false)
    private String type;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private BoothType boothType;

    @ColumnDefault("false")
    private boolean is_deleted;

    @JsonManagedReference
    @OneToMany(mappedBy = "booth", fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "booth")
    private List<Likes> likes = new ArrayList<>();

//    @JsonManagedReference
//    @OneToMany(mappedBy = "booth")
//    private List<Menu> menus = new ArrayList<>();



    //test를 위한 change 함수
    public void change(String booth_title, String booth_content, boolean is_deleted){
        this.booth_title = booth_title;
        this.booth_content = booth_content;
        this.is_deleted = is_deleted;
    }

}
