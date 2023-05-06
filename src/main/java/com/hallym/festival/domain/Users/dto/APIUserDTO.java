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


    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    private String mid;

    @NonNull
    private String password;

    @NonNull
    private String name;

    @NonNull
    private String department;

    @NonNull
    private String club;

    @NonNull
    private String phone;

    private String role;

    public APIUserDTO(String username, String password, String name, String department, String club, String phone, String role, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.mid = username;
        this.password = password;
        this.name = name;
        this.department = department;
        this.club = club;
        this.phone = phone;
        this.role = role;
    }



    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
}