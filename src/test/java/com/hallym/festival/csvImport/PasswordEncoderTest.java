package com.hallym.festival.csvImport;

import com.hallym.festival.global.security.Encrypt;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
public class PasswordEncoderTest{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("패스워드 암호화 테스트")
    @Test
    public void testEncrypt() {
        Encrypt encrypt = new Encrypt();
        String plainTextPwd = "test1234";
        String encryptedPwd = encrypt.getEncrypt(plainTextPwd);

        log.info("Plain Text Password: {}", plainTextPwd);
        log.info("Encrypted Password: {}", encryptedPwd);

        assertEquals(encryptedPwd, encrypt.getEncrypt(plainTextPwd));
    }
}