package com.hallym.festival.csvImport;

import com.hallym.festival.domain.Users.entity.APIUser;
import com.hallym.festival.domain.Users.entity.MemberRole;
import com.hallym.festival.domain.Users.repository.APIUserRepository;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.entity.DayNight;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Transactional
@Log4j2
public class UserImportTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private APIUserRepository apiUserRepository;

    @Autowired
    private BoothRepository boothRepository;

    @BeforeEach
    public void setUp() {
        apiUserRepository.deleteAll();
        boothRepository.deleteAll();
    }

    @Test
    public void testCsvReader() throws IOException, CsvValidationException {

        // csv 파일을 읽어올 InputStream 생성
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/csv/userData.csv");

        // CSVReader 생성
        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .withSkipLines(1)
                .build();

        // CSV에서 읽어온 데이터를 APIUser 엔티티로 변환하여 DB에 저장
        String[] line;
        while ((line = reader.readNext()) != null) {
            APIUser apiUser = new APIUser();
            Set<MemberRole> roles = Collections.singleton(MemberRole.USER);
            if ("총학생회".equals(line[4]) || "개발팀".equals(line[4])) {
                roles = Collections.singleton(MemberRole.ADMIN);
            }

            apiUser.addUser(line[0], //mid
                    passwordEncoder.encode(line[1]), //password
                    line[2], //name
                    line[3], //department
                    line[4], //club
                    line[5], //phone
                    roles
            );

            apiUserRepository.save(apiUser);

            if (!"총학생회".equals(line[4]) || "개발팀".equals(line[4])) { // Booth 객체 생성 조건
                Booth booth = boothRepository.save(Booth.builder()
                        .booth_title(line[6])
                        .booth_content("동아리 소개...")
                        .writer(line[0])
                        .booth_type(BoothType.부스)
                        .dayNight(DayNight.valueOf(line[7]))
                        .openDay(line[8])
                        .build());
                boothRepository.save(booth);
            }
        }

        // DB에 저장된 데이터 확인
        List<APIUser> apiUsers = apiUserRepository.findAll(PageRequest.of(1, 10)).getContent();
        log.info(apiUsers);
    }
}