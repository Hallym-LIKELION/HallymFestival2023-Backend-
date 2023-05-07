package com.hallym.festival.global.security.exception;

import com.google.gson.Gson;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class AccessTokenException extends RuntimeException {

    TOKEN_ERROR token_error;

    public enum TOKEN_ERROR {
        UNACCEPT(401,"Token is null or too short"), //토큰이 null이거나 너무 짧을 때 발생하는 예외
        BADTYPE(401, "Token type Bearer"), //토큰의 타입이 잘못되었을 때 발생하는 예외
        MALFORM(403, "Malformed Token"), //토큰의 형식이 잘못되었을 때 발생하는 예외
        BADSIGN(403, "BadSignatured Token"), //토큰의 서명(signature)이 올바르지 않을 때 발생하는 예외
        EXPIRED(403, "Expired Token"); //토큰이 만료되었을 때 발생하는 예외

        private int status;
        private String msg;

        //TOKEN_ERROR 생성자
        TOKEN_ERROR(int status, String msg){
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return this.status;
        }

        public String getMsg() {
            return this.msg;
        }
    }

    public AccessTokenException(TOKEN_ERROR error){
        super(error.name());
        this.token_error = error;
    }

    public void sendResponseError(HttpServletResponse response){

        response.setStatus(token_error.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String responseStr = gson.toJson(Map.of("msg", token_error.getMsg(), "time", new Date()));

        try {
            response.getWriter().println(responseStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
