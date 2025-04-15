package com.Tripia.tourapi.location.repository;

import com.Tripia.tourapi.location.entity.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaCodeRepository extends JpaRepository<AreaCode,Long> {
    Optional<AreaCode> findByName(String name);
    Optional<AreaCode> findByCode(String code);
}
