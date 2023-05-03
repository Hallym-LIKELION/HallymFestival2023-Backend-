package com.hallym.festival.domain.menu.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MenuSell {
    SOID, SELL;

    @JsonCreator
    public static MenuSell from(String s) {
        return MenuSell.valueOf(s);
    }
}
