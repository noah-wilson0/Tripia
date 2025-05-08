package com.Tripia.route.service;

import com.Tripia.route.entity.TravelPlanDay;
import com.Tripia.route.repository.TravelPlanDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelPlanDayService {

    private final TravelPlanDayRepository travelPlanDayRepository;

    public TravelPlanDay save(TravelPlanDay travelPlanDay) {
        return travelPlanDayRepository.save(travelPlanDay);
    }

    public Optional<TravelPlanDay> findById(Long id) {
        return travelPlanDayRepository.findById(id);
    }

    public List<TravelPlanDay> findAll() {
        return travelPlanDayRepository.findAll();
    }

}
