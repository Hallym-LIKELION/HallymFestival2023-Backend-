package com.hallym.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Long bno;

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

    @OneToMany(mappedBy = "booth",
            cascade = {CascadeType.ALL}, //booth 엔티티의 모든 상태 변화에 BoothImage 객체도 같이 변경
            fetch = FetchType.LAZY,
            orphanRemoval = true) //수정 기능에서 하위 엔티티의 참조가 없는 상태가 되면 삭제되기 위해 orphanRemoval 속성 true)
    @Builder.Default //imageSet 인스턴스 생성 시 BoothImage 값으로 초기화하기 위함
    private Set<BoothImage> imageSet = new HashSet<>();

    // Booth 객체 자체에서 BoothImage 객체들을 관리하기 위한 addImage, clearImages
    public void addImage(String uuid, String fileName){

        BoothImage boothImage = BoothImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .booth(this) //양방향의 경우 참조 관계가 서로 일치하도록
                .ord(imageSet.size())
                .build();
        imageSet.add(boothImage);
    }

    public void clearImages() {

        imageSet.forEach(boothImage -> boothImage.changeBoard(null));

        this.imageSet.clear();
    }


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
