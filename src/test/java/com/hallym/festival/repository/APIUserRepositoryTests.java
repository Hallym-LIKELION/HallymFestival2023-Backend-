package com.hallym.festival.repository;

import com.hallym.festival.domain.Users.entity.APIUser;
import com.hallym.festival.domain.Users.entity.MemberRole;
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

    @DisplayName("아이디 더미 생성 테스트")
    @Test
    public void testAdminInserts() {
        IntStream.rangeClosed(1,10).forEach(i -> {
            APIUser apiUser = APIUser.builder()
                    .mid("2015434"+i)
                    .password( passwordEncoder.encode("1111") )
                    .club("소속부스"+i)
                    .department("빅데이터"+i)
                    .name("홍길동"+i)
                    .phone("010-5213-123"+i)
                    .build();

            apiUser.addRole(MemberRole.USER);

            apiUserRepository.save(apiUser);
        });
    }

    @DisplayName("ADMIN 아이디 생성 테스트 - ")
    @Test
    public void testInsertAdminOnes() {
            APIUser apiUser = APIUser.builder()
                    .mid("admin")
                    .password( passwordEncoder.encode("1111") )
                    .club("소속부스")
                    .department("축제준비위원회")
                    .name("홍길동")
                    .phone("010-5213-1231")
                    .build();

            apiUser.addRole(MemberRole.ADMIN);

            apiUserRepository.save(apiUser);
        };

    @DisplayName("USER 아이디 생성 테스트 -")
    @Test
    public void testInsertUserOnes() {
        APIUser apiUser = APIUser.builder()
                .mid("20195170")
                .password( passwordEncoder.encode("1111") )
                .club("소속부스")
                .department("축제준비위원회")
                .name("박주영")
                .phone("010-9059-4356")
                .build();

        apiUser.addRole(MemberRole.USER);
        apiUserRepository.save(apiUser);
    };

    @DisplayName("축제 관리자 계정 등록")
    @Test
    public void UserInput() {
        APIUser apiUser = APIUser.builder()
                .mid("20202818")
                .password( passwordEncoder.encode("5dJ8Hm") )
                .club("축제준비위원회")
                .department("경제학과")
                .name("박시언")
                .phone("010-8458-1248")
                .build();

        apiUser.addRole(MemberRole.USER);
        apiUserRepository.save(apiUser);
    };

}
