package com.Tripia.tourapi.place.repository;


import com.Tripia.tourapi.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Place findByTitle(String title);
    List<Place> findAllByContentIdIn(List<String> contentIds);
    Optional<Place> findByContentId(String contentId);
}
