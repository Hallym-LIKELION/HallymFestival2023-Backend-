package com.hallym.festival.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    WRONG_BOOTH_ID(HttpStatus.BAD_REQUEST.value(), "해당 ID의 Booth가 없습니다."),
    WRONG_COMMENT_ID(HttpStatus.BAD_REQUEST.value(), "해당 ID의 Comment가 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    WRONG_LIKE_KEY(HttpStatus.NOT_FOUND.value(), "해당 쿠키가 존재하지 않습니다."),
    WRONG_NOTIFICATION_ID(HttpStatus.BAD_REQUEST.value(), "해당 ID의 Notification이 없습니다."),
    WRONG_MENU_ID(HttpStatus.BAD_REQUEST.value(), "해당 ID의 Menu가 없습니다.");

    private final int code;
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
