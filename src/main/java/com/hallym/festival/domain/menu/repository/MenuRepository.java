package com.hallym.festival.domain.menu.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByBooth_Bno(Long bno);
}
