package com.hallym.festival.domain.Users.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole {

    USER("ROLE_USER"), //부스 운영 관리자
    ADMIN("ROLE_ADMIN"), ; //축제 준비 위원회(총 관리자)

    private String value;

}
