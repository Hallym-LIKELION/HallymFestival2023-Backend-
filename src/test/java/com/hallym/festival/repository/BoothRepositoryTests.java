package com.hallym.festival.repository;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoothRepositoryTests {

	@Autowired
	private BoothRepository boothRepository;

	@Test
	public void testInsert() {
		IntStream.rangeClosed(1,5).forEach(i -> {
			Booth booth = Booth.builder()
					.booth_title("부스명..." + i)
					.booth_content("동아리 소개..." + i)
					.type("부스타입"+(i % 3))
					.build();

			Booth result = boothRepository.save(booth);

			log.info("-------------------save-----------");
			log.info(result);
		});
	}

	@Test
	public void testSelect() {
		Long bno = 20L;

		Optional<Booth> result = boothRepository.findById(bno);

		Booth booth = result.orElseThrow();

		log.info("----------------");
		log.info(booth);

	}


	@Test
	public void testUpdate() {

		Long bno = 2L;

		Optional<Booth> result = boothRepository.findById(bno);

		Booth booth = result.orElseThrow();

		booth.change("농구 동아리", "3점 슛의 황태자는 누구?", true);

		boothRepository.save(booth);

	}

	@Test
	public void testDelete() {
		Long bno = 10L;

		boothRepository.deleteById(bno);
	}



}
