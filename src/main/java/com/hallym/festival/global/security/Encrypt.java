package com.hallym.festival.global.security;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Encrypt {
    public String getEncrypt(String pwd){
        StringBuilder sb = new StringBuilder();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((pwd).getBytes());
            byte[] pwsalt = md.digest();

            for (byte b : pwsalt) {
                sb.append(String.format("%02x",b));
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
