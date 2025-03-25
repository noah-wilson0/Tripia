package com.Tripia.tourapi.location.repository;

import com.Tripia.tourapi.location.entity.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaCodeRepository extends JpaRepository<AreaCode,Long> {
    AreaCode findByName(String name);
    AreaCode findByCode(String code);
}
