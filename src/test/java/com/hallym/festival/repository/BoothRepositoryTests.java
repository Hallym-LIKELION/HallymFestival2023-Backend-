package com.hallym.festival.repository;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.entity.BoothImage;
import com.hallym.festival.domain.booth.entity.BoothType;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import com.hallym.festival.domain.comment.repository.CommentRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoothRepositoryTests {

	@Autowired
	private BoothRepository boothRepository;

	@Autowired
	private CommentRepository commentRepository;

//	@BeforeEach
//	void setUp() {
//		boothRepository.deleteAll();
//	}

	//등록 테스트
	@Test
	public void testInsert() {
		IntStream.rangeClosed(1, 30).forEach(i -> {

			Booth result = boothRepository.save(Booth.builder()
					.booth_title("부스명..." + i)
					.booth_content("동아리 소개..." + i)
					.booth_type(BoothType.푸드트럭)
					.writer("부스담당매니저" + (i % 5))
					.build());

			log.info("-------------------save-----------");
			log.info(result);
		});
	}

	//조회 테스트
	@Test
	public void testSelect() {
		Long bno = 20L;

		Optional<Booth> result = boothRepository.findById(bno);

		Booth booth = result.orElseThrow();

		log.info("----------------");
		log.info(booth);

	}

	//수정 테스트
	@Test
	public void testUpdate() {

		Long bno = 3L;

		Optional<Booth> result = boothRepository.findById(bno);

		Booth booth = result.orElseThrow();

		booth.change("현재 3시 01분", "3점 슛의 황태자는 누구?", "한림대듀란트", BoothType.주점, false);

		boothRepository.save(booth);
		log.info(booth);
	}

	//삭제 테스트
	@Test
	public void testDelete() {

		Long bno = 2L;

		Optional<Booth> target = boothRepository.findById(bno);

		Booth booth = target.orElseThrow();

		boothRepository.deleteById(bno);

		log.info(booth);

	}

	//첨부파일이 있는 게시물 등록 테스트
	@Test
	public void testInsertWithImages() {

		Booth booth = Booth.builder()
				.booth_title("Image Test")
				.booth_content("첨부파일 테스트")
				.writer("tester")
				.booth_type(BoothType.주점)
				.build();

		for (int i = 0; i < 3; i++) {

			booth.addImage(UUID.randomUUID().toString(), "file" + i + ".jpg");

		}//end for

		boothRepository.save(booth);

		log.info(booth.getImageSet());
	}

//	@Test
//	public void testReadWithImages() {
//
//		//반드시 존재하는 bno로 확인
//		Optional<Booth> result = boothRepository.findById(1L);
//
//		Booth booth = result.orElseThrow();
//
//		log.info(booth);
//		log.info("--------------------");
//		log.info(booth.getImageSet());
//
//	}

	//게시물과 첨부파일 조회
	@Test
	public void testReadWithImages() {

		//반드시 존재하는 bno로 확인
		Optional<Booth> result = boothRepository.findByIdWithImages(1L);

		Booth booth = result.orElseThrow();

		log.info(booth);
		log.info("--------------------");
		for (BoothImage boothImage : booth.getImageSet()) {
			log.info(boothImage); //테이블 실행 결과가 left_outer join 처리가 되었는지 확인하세욧
		}
	}

	//첨부파일 수정
	@Transactional
	@Commit
	@Test
	public void testModifyImages() {

		Optional<Booth> result = boothRepository.findByIdWithImages(1L);

		Booth booth = result.orElseThrow();

		//기존의 첨부파일들은 삭제
		booth.clearImages();

		//새로운 첨부파일들
		for (int i = 0; i < 2; i++) {

			booth.addImage(UUID.randomUUID().toString(), "updatefile"+i+".jpg");
		}

		boothRepository.save(booth);

	}

	@Test
	@Transactional
	@Commit
	public void testRemoveAll() {

		Long bno = 1L;

		commentRepository.deleteByBooth_Bno(bno);

		boothRepository.deleteById(bno);

	}
}
