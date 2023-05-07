package com.hallym.festival.domain.Users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Id;
import java.util.Collection;

@Getter
@Setter
@ToString
public class APIUserDTO extends User {

    @Id
    @NonNull
    private String mid;

    @NonNull
    private String password;

    @NonNull
    private String name;

    private String department;

    private String club;

    private String phone;

    public APIUserDTO(String username, String password, String name, String department, String club, String phone, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.mid = username;
        this.password = password;
        this.name = name;
        this.department = department;
        this.club = club;
        this.phone = phone;
    }


}