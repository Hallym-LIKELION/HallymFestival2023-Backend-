package com.hallym.festival.domain.Users;

import com.hallym.festival.domain.Users.entity.APIUser;
import com.hallym.festival.domain.Users.entity.MemberRole;
import com.hallym.festival.domain.Users.repository.APIUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ToString
@Service
@Log4j2
public class APIUserService {

    private final APIUserRepository apiUserRepository;

    public String getRoleSetByMid(String mid) {
        APIUser apiUser = apiUserRepository.findByUserId(mid);
        String role = String.join(",", apiUser.getRoleSet().stream().map(MemberRole::getValue).collect(Collectors.toList()));

        log.info("해당 유저는 " + role + " 권한을 가지고 있습니다.");

        if (apiUser != null) {
            return role;
        } else {
            return "apiUser 정보를 제대로 가져오지 못했습니다";
        }
    }



}
