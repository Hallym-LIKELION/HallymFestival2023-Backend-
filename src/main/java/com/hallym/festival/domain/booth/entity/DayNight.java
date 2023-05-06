package com.hallym.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DayNight {
    DAY,
    NIGHT;
    @JsonCreator
    public static DayNight from(String s) {
        return DayNight.valueOf(s);
    }
}
