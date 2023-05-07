package com.hallym.festival.domain.totalvisit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "totalvisit")
@Builder
@EntityListeners(value = { AuditingEntityListener.class }) //Audting 기능 추가
@Entity
public class TotalVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tno")
    private Long tno;

    @CreatedDate //생성시간 자동저장
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "visitDate")
    private LocalDate visitDate;

    @CreatedDate //생성시간 자동저장
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "visitDateDetail")
    private LocalDateTime visitDateDetail;
}
