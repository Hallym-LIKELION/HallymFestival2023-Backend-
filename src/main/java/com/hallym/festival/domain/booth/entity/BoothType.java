package com.hallym.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BoothType {
    주점, 푸드트럭, 부스, 플리마켓;

    @JsonCreator
    public static BoothType from(String s) {
        return BoothType.valueOf(s);
    }
}
