package com.hallym.festival.domain.booth.service;

import com.hallym.festival.domain.booth.entity.Booth;
import com.hallym.festival.domain.booth.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoothService {

    private final BoothRepository boothRepository;

    public List<Booth> getAllBooths() {
        return boothRepository.findAll();
    }
}