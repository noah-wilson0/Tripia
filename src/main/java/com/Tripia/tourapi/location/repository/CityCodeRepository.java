package com.Tripia.tourapi.location.repository;

import com.Tripia.tourapi.location.entity.CityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityCodeRepository extends JpaRepository<CityCode, Long> {

    public Optional<CityCode> findByCityCode(String cityCode);
    public Optional<CityCode> findByName(String name);

}
