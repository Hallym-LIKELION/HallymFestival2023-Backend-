package com.hallym.festival.domain.notice.repository;

import com.hallym.festival.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
