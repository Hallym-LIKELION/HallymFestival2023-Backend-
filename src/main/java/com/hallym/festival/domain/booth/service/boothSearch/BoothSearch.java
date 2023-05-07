package com.hallym.festival.domain.booth.service.boothSearch;

import com.hallym.festival.domain.booth.dto.BoothListAllDTO;
import com.hallym.festival.domain.booth.entity.Booth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoothSearch {

    Page<BoothListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);

}
