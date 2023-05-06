package com.hallym.festival.domain.Users.repository;

import com.hallym.festival.domain.Users.entity.APIUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface APIUserRepository extends JpaRepository<APIUser, String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from APIUser m where m.mid = :mid")
    Optional<APIUser> getWithRoles(@Param("mid") String mid); //로그인 시 Role도 같이 로딩


}
