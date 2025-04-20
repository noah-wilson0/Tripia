package com.Tripia.tourapi.touristspot.service;

import com.Tripia.tourapi.touristspot.entity.TourSpot;
import com.Tripia.tourapi.touristspot.repository.TourSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TourSpotService {

    private final TourSpotRepository tourSpotRepository;

    public TourSpot save(TourSpot tourSpot) {
        return tourSpotRepository.save(tourSpot);
    }

    public Optional<TourSpot> findById(Long id) {
        return tourSpotRepository.findById(id);
    }

    public Optional<TourSpot> findByContentId(String contentId) {
        return tourSpotRepository.findByContentId(contentId);
    }

    public Optional<TourSpot> findByTitle(String title) {
        return tourSpotRepository.findByTitle(title);
    }

    public List<TourSpot> findByAreaCode(String areaCode) {
        return tourSpotRepository.findByAreaCode(areaCode);
    }

    public List<TourSpot> findAll() {
        return tourSpotRepository.findAll();
    }
}
