package com.hallym.festival.global.security;

import com.hallym.festival.domain.Users.dto.APIUserDTO;
import com.hallym.festival.domain.Users.entity.APIUser;
import com.hallym.festival.domain.Users.repository.APIUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {

    //주입
    private final APIUserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<APIUser> result = apiUserRepository.findById(username);

        APIUser apiUser = result.orElseThrow(() -> new UsernameNotFoundException("해당 아이디는 없는 사용자입니다."));

        log.info("APIUserDetailsService apiUser-------------------------------------");

        APIUserDTO dto =
                new APIUserDTO(
                        apiUser.getMid(),
                        apiUser.getPassword(),
                        apiUser.getName(),
                        apiUser.getDepartment(),
                        apiUser.getClub(),
                        apiUser.getPhone(),
                        apiUser.getRoleSet()
                                .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                                .collect(Collectors.toList())
                );

        log.info(dto);

        return dto;
    }
}
