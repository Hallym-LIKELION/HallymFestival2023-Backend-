package com.hallym.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hallym.festival.domain.comment.entity.Comment;
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
    @Column(name = "bno")
    private Long id;

    @Column(length = 30, nullable = false) //컬럼의 길이와 null허용여부
    private String booth_title;

    @Column(length = 500, nullable = false)
    private String booth_content;

    @Column(length = 50, nullable = false)
    private String writer;

//    @Column(length = 30, nullable = false)
//    private String booth_type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoothType booth_type;

    @ColumnDefault("true") //개점 여부
    private Boolean active;

    @ColumnDefault("false") //삭제 여부
    private boolean is_deleted;

    @JsonManagedReference
    @OneToMany(mappedBy = "booth", fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();


//    @JsonManagedReference
//    @OneToMany(mappedBy = "booth")
//    private List<Menu> menus = new ArrayList<>();

//    @OneToMany(mappedBy = "booth")
//    private List<Like> likes = new ArrayList<>();

    //test를 위한 change 함수
    public void change(String booth_title, String booth_content, String writer, BoothType booth_type, boolean active){
        this.booth_title = booth_title;
        this.booth_content = booth_content;
        this.writer = writer;
        this.booth_type = booth_type;
        this.active = active;
    }

}
