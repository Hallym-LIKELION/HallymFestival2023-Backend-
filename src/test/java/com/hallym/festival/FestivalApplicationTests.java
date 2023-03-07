package com.hallym.festival;

import com.hallym.festival.domain.booth.Booth;
import com.hallym.festival.domain.booth.BoothRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
class FestivalApplicationTests {

	@Autowired
	private BoothRepository boothRepository;

	@Test
	public void insertTests() throws Exception{

		Booth booth = Booth.builder()
				.name("물풍선던기지")
				.description("대신 맞아드립니다.")
				.image("http://example.com/test11.png")
				.build();

		log.info("------------------");
		log.info(booth);

		Booth savedBooth = boothRepository.save(booth);

		List<Booth> boothList = boothRepository.findAll();

		log.info("-------------------save-----------");
		log.info(savedBooth);

		log.info(boothList);

	}


}
