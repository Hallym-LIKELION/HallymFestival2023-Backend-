package com.hallym.festival.domain.likes.repository;

import com.hallym.festival.domain.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByCookieKey(String cookieKey);
    int countLikesByBooth_bno(Long bno);
}
