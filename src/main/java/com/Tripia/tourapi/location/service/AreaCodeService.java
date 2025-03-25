package com.Tripia.tourapi.location.service;


import com.Tripia.tourapi.location.entity.AreaCode;
import com.Tripia.tourapi.location.repository.AreaCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AreaCodeService {

    private final AreaCodeRepository areaCodeRepository;

    public AreaCode save(AreaCode areaCode) {
        return areaCodeRepository.save(areaCode);
    }

    public AreaCode findByCode(String code) {
        return areaCodeRepository.findByCode(code);
    }

    public AreaCode findByName(String name) {

        return areaCodeRepository.findByName(name);
    }

    public List<AreaCode> findAll() {
        return areaCodeRepository.findAll();
    }

}
