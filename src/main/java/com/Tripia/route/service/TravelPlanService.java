package com.Tripia.route.service;

import com.Tripia.route.entity.TravelPlan;
import com.Tripia.route.repository.TravelPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;

    public TravelPlan save(TravelPlan travelPlan) {
        return travelPlanRepository.save(travelPlan);
    }
    public Optional<TravelPlan> findById(Long id) {
        return travelPlanRepository.findById(id);
    }

    public List<TravelPlan> findAll() {
        return travelPlanRepository.findAll();
    }
}
