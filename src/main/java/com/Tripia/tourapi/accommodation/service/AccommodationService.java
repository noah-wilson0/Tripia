package com.Tripia.tourapi.accommodation.service;

import com.Tripia.tourapi.accommodation.entity.Accommodation;
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

    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }


    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElse(null);
    }

    public Optional<Accommodation> findByContentId(String contentId) {
        return accommodationRepository.findByContentId(contentId);
    }

    public Accommodation findByTitle(String title) {
        return accommodationRepository.findByTitle(title);
    }

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public List<Accommodation> findAllByContentIdIn(List<String> contentIds) {
        return accommodationRepository.findAllByContentIdIn(contentIds);
    }

}
