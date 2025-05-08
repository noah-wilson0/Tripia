package com.Tripia.route.service;

import com.Tripia.route.entity.TravelPlanRoute;
import com.Tripia.route.repository.TravelPlanRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelPlanRouteService {

    private final TravelPlanRouteRepository travelPlanRouteRepository;

    public TravelPlanRoute save(TravelPlanRoute travelPlanRoute) {
        return travelPlanRouteRepository.save(travelPlanRoute);
    }

    public Optional<TravelPlanRoute> findById(Long id) {
        return travelPlanRouteRepository.findById(id);
    }

    public List<TravelPlanRoute> findAll() {
        return travelPlanRouteRepository.findAll();
    }
}
