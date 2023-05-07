package com.hallym.festival.repository;

import com.hallym.festival.domain.Users.dto.APIUserDTO;
import com.hallym.festival.domain.Users.entity.APIUser;
import com.hallym.festival.domain.Users.repository.APIUserRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserImportTest {

    @Autowired
    private APIUserRepository apiUserRepository;

    @BeforeEach
    public void setUp() {
        apiUserRepository.deleteAll();
    }

    @Test
    public void testCsvReader() throws IOException {
        // csv 파일을 읽어올 InputStream 생성
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.csv").getFile());
        FileInputStream inputStream = new FileInputStream(file);

        // CSVReader 생성
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReaderBuilder(streamReader).withSkipLines(1).build();

        // CSV에서 읽어온 데이터를 APIUser 엔티티로 변환하여 DB에 저장
        String[] line;
        while ((line = reader.readNext()) != null) {
            APIUserDTO apiUserDTO = new APIUserDTO();
            apiUserDTO.setMid(line[0]);
            apiUserDTO.setName(line[1]);
            apiUserDTO.setDepartment(line[2]);
            apiUserDTO.setPhone(line[3]);
//            apiUserRepository.save(apiUser);
        }

        // DB에 저장된 데이터 확인
        List<APIUser> apiUsers = apiUserRepository.findAll();
        Assertions.assertEquals(3, apiUsers.size());
    }
}