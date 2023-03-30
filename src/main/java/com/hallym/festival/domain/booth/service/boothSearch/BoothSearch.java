package com.hallym.festival.domain.booth.service.boothSearch;

import com.hallym.festival.domain.booth.entity.Booth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoothSearch {

    Page<Booth> search1(Pageable pageable);

    Page<Booth> searchAll(String[] types, String keyword, Pageable pageable);

}
