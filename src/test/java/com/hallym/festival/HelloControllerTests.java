package com.hallym.festival;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hallym.festival.web.HelloController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class) //
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 HTTP GET 요청
                .andExpect(status().isOk()) //HTTP Header의 status를 검증
                .andExpect(content().string(hello)); //응답 본문의 내용을 검증(Controller에서 "hello" 리턴)

    }
}
