package com.hallym.festival.repository;

import com.hallym.festival.domain.Users.entity.APIUser;
import com.hallym.festival.domain.Users.entity.UserRole;
import com.hallym.festival.domain.Users.repository.APIUserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class APIUserRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private APIUserRepository apiUserRepository;

    @DisplayName("가상 아이디 생성 테스트")
    @Test
    public void testInserts() {
        IntStream.rangeClosed(1,10).forEach(i -> {
            APIUser apiUser = APIUser.builder()
                    .mid("2015434"+i)
                    .password( passwordEncoder.encode("1111") )
                    .club("소속부스"+i)
                    .department("빅데이터"+i)
                    .name("홍길동"+i)
                    .phone("010-5213-123"+i)
                    .role("ROLE_ADMIN")
                    .build();

            apiUserRepository.save(apiUser);
        });
    }
}
