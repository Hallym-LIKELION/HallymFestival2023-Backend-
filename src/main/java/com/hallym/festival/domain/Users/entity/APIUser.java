package com.hallym.festival.domain.Users.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class APIUser {

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String mid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "department")
    private String department;

    @Column(name = "club")
    private String club;

    @Column(name = "phone")
    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>(); //권한 정보

    @ColumnDefault("false") //삭제 여부
    private boolean is_deleted;

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
