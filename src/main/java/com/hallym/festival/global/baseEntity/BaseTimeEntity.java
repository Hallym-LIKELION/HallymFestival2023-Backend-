package com.hallym.festival.global.baseEntity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //Entity들이 상속할 경우 해당 클래스 필드들도 칼럼으로 인식
@EntityListeners(value = { AuditingEntityListener.class }) //Audting 기능 추가
public class BaseTimeEntity {

    @CreatedDate //생성시간 자동저장
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate //엔티티 변경시간 자동저장
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name ="moddate" )
    private LocalDateTime modDate;
}
