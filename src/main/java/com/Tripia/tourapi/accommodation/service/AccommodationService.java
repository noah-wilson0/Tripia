package com.Tripia.tourapi.accommodation.service;

import com.Tripia.tourapi.accommodation.entity.AccommodationInfo;
import com.Tripia.tourapi.accommodation.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    public AccommodationInfo save(AccommodationInfo accommodationInfo) {
        return accommodationRepository.save(accommodationInfo);
    }


    public AccommodationInfo findById(Long id) {
        return accommodationRepository.findById(id).orElse(null);
    }

    public Optional<AccommodationInfo> findByContentId(String contentId) {
        return accommodationRepository.findByContentId(contentId);
    }

    public AccommodationInfo findByTitle(String title) {
        return accommodationRepository.findByTitle(title);
    }

    public List<AccommodationInfo> findAll() {
        return accommodationRepository.findAll();
    }

    public List<AccommodationInfo> findAllByContentIdIn(List<String> contentIds) {
        return accommodationRepository.findAllByContentIdIn(contentIds);
    }

}
