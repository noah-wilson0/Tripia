package com.Tripia.tourapi.accommodation.repository;

import com.Tripia.tourapi.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    Accommodation findByTitle(String title);
    List<Accommodation> findAllByContentIdIn(List<String> contentIds);
    Optional<Accommodation> findByContentId(String contentId);
}
