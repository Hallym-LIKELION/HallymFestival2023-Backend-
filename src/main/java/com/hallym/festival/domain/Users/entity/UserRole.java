package com.hallym.festival.domain.Users.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"), ;

    private String value;

    @JsonCreator
    public static UserRole from(String s) {
        return UserRole.valueOf(s);
    }
}
