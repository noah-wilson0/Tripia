package com.Tripia.route.repository;

import com.Tripia.route.entity.TravelPlanPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelPlanPlaceRepository extends JpaRepository<TravelPlanPlace, Long> {

    List<TravelPlanPlace> findByTravelPlanDayIdOrderBySequenceAsc(Long id);

}
