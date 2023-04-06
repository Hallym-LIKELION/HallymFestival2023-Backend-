package com.hallym.festival.domain.menu.repository;

import com.hallym.festival.domain.comment.entity.Comment;
import com.hallym.festival.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m where m.booth.bno=:bno and m.is_deleted=:is_deleted")
    List<Menu> findByBooth_Bno(@Param("bno") Long bno,@Param("is_deleted") Boolean is_deleted);
}
