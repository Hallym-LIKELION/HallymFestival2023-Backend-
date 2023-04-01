package com.hallym.festival.domain.menu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.global.baseEntity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Menu extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private Long price;

    @ColumnDefault("false")
    private boolean is_deleted;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public void updateMenu(Menu menu) {
        this.name = menu.name;
        this.price = menu.price;
    }
}
