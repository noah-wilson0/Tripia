package com.Tripia.tourapi.location.service;

import com.Tripia.tourapi.location.entity.CityCode;
import com.Tripia.tourapi.location.repository.CityCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityCodeService {
    private final CityCodeRepository cityCodeRepository;

    public CityCode save(CityCode cityCode) {
        return cityCodeRepository.save(cityCode);
    }

    public Optional<CityCode> findByCode(String cityCode) {
        return cityCodeRepository.findByCityCode(cityCode);
    }
    public Optional<CityCode> findByName(String name) {
        return cityCodeRepository.findByName(name);
    }
}
