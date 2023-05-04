package com.hallym.festival.domain.visitcomment.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hallym.festival.domain.booth.entity.BoothActive;

public enum Color {
    YELLOW, PINK;

    @JsonCreator
    public static Color from(String s) {
        return Color.valueOf(s);
    }
}
