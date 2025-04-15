package com.Tripia.tourapi.accommodation.repository;

import com.Tripia.tourapi.accommodation.entity.AccommodationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<AccommodationInfo, Long> {

    AccommodationInfo findByTitle(String title);
    List<AccommodationInfo> findAllByContentIdIn(List<String> contentIds);
    Optional<AccommodationInfo> findByContentId(String contentId);
}
