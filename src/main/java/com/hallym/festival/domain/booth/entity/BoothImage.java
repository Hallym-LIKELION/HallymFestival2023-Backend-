package com.hallym.festival.domain.booth.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "booth") //Booth 필드는 toString에서 제외
public class BoothImage implements Comparable<BoothImage>{ //순번에 맞게 정렬하기 위해 Comparable 인터페이스 적용

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Booth booth;


    @Override
    public int compareTo(BoothImage other) {
        return this.ord - other.ord;
    }

    public void changeBooth(Booth booth ){ //Booth 엔티티 삭제 시 BoothImage 객체의 참조도 변경하기 위함
        this.booth = booth;
    }

}