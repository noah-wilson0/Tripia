package com.Tripia.route.service;

import com.Tripia.route.entity.TravelPlanDay;
import com.Tripia.route.entity.TravelPlanPlace;
import com.Tripia.route.repository.TravelPlanPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelPlanPlaceService {

    private final TravelPlanPlaceRepository travelPlanPlaceRepository;

    public TravelPlanPlace save(TravelPlanPlace travelPlanPlace) {
        return travelPlanPlaceRepository.save(travelPlanPlace);
    }
    public Optional<TravelPlanPlace> findById(Long id) {
        return travelPlanPlaceRepository.findById(id);
    }
    public List<TravelPlanPlace> findAll() {
        return travelPlanPlaceRepository.findAll();
    }

    public List<TravelPlanPlace> findAllByTravelPlanDay(Long travelPlanDayId) {
        return travelPlanPlaceRepository.findByTravelPlanDayIdOrderBySequenceAsc(travelPlanDayId);
    }
}
