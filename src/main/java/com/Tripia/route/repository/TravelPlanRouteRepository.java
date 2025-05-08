package com.Tripia.route.repository;

import com.Tripia.route.entity.TravelPlanRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPlanRouteRepository extends JpaRepository<TravelPlanRoute, Long> {
}
