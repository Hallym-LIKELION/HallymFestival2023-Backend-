package com.hallym.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.likes.entity.Likes;
import com.hallym.festival.domain.menu.entity.Menu;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.*;

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

    @NonNull
    @Column(length = 30) //컬럼의 길이와 null허용여부
    private String booth_title;

    @NonNull
    @Column(length = 500)
    private String booth_content;

    @NonNull
    @Column(length = 50)
    private String writer;

//    @Column(length = 30, nullable = false)
//    private String booth_type;

    @Enumerated(EnumType.STRING)
    @NonNull
    private BoothType booth_type;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Column(columnDefinition = "varchar(32) default 'OPEN'" )
    private BoothActive booth_active; //개점 여부
    @ColumnDefault("false") //삭제 여부
    private boolean is_deleted;

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "booth", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "booth",
            cascade = {CascadeType.ALL}, //booth 엔티티의 모든 상태 변화에 BoothImage 객체도 같이 변경
            fetch = FetchType.LAZY,
            orphanRemoval = true) //수정 기능에서 하위 엔티티의 참조가 없는 상태가 되면 삭제되기 위해 orphanRemoval 속성 true)
    @Builder.Default //imageSet 인스턴스 생성 시 BoothImage 값으로 초기화하기 위함
    private Set<BoothImage> imageSet = new HashSet<>();

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "booth",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    private List<Menu> menus = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "booth")
    private List<Likes> likes = new ArrayList<>();

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

    //test를 위한 change 함수
    public void change(String booth_title, String booth_content, String writer, BoothType booth_type){
        this.booth_title = booth_title;
        this.booth_content = booth_content;
        this.writer = writer;
        this.booth_type = booth_type;
    }

    //부스 개폐 상태 set
    public void setActive(BoothActive booth_active){
        this.booth_active = booth_active;
    }

    //부스 삭제 상태 set
    public void setIs_deleted(Boolean is_deleted){
        this.is_deleted = is_deleted;
    }
}
