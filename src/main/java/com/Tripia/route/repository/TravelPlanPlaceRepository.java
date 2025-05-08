package com.Tripia.route.repository;

import com.Tripia.route.entity.TravelPlanPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPlanPlaceRepository extends JpaRepository<TravelPlanPlace, Long> {
}
