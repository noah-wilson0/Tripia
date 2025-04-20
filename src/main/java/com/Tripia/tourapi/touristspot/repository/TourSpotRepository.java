package com.Tripia.tourapi.touristspot.repository;

import com.Tripia.tourapi.touristspot.entity.TourSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourSpotRepository extends JpaRepository<TourSpot, Long> {

    public Optional<TourSpot> findByContentId(String contentId);

    public Optional<TourSpot> findByTitle(String title);

    public List<TourSpot> findByAreaCode(String areaCode);

}
