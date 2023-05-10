package com.hallym.festival.domain.likes.repository;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.likes.entity.Likes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByCookieKey(String cookieKey);
    int countLikesByBooth_bno(Long bno);
}
