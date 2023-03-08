package com.hallym.festival.domain.booth;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hallym.festival.domain.comment.Comment;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booth")
public class Booth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int type;
    @NotNull
    private String name;

    @NotNull
    private String description;

    private String image;

    @JsonManagedReference
    @OneToMany(mappedBy = "booth")
    private List<Comment> comments = new ArrayList<>();


    private LocalDateTime regDate;

    private LocalDateTime updateDate;


}
