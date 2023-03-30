package com.hallym.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BoothActive {
    OPEN, CLOSE;

    @JsonCreator
    public static BoothActive from(String s) {
        return BoothActive.valueOf(s);
    }
}
