package com.Tripia.tourapi.scheduler.accommodation.service;

import com.Tripia.tourapi.accommodation.entity.AccommodationInfo;
import com.Tripia.tourapi.accommodation.service.AccommodationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@Transactional
class AccommodationSyncServiceTest {

    @Autowired
    private  AccommodationSyncService accommodationSyncService;

    @Autowired
    private AccommodationService accommodationService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("TOUR API의 /searchStay1 동기화 후 DB 저장 여부 확인")
    void syncAccommodation() {
        accommodationSyncService.syncAccommodation();
    }

    @Test
    @DisplayName("동기화된 TOUR API의 /searchStay1 데이터 업데이트 확인")
    void updateAccommodation() {
        String updateTitle="업데이트 확인용";

        // 1. 테스트용 데이터 저장
        AccommodationInfo newAccommodation = new AccommodationInfo(
                null, "tempContentId", "01", "테스트숙소",
                "서울특별시 어딘가", null, null,
                "126.123", "37.123", "010-1234-5678"
        );
        AccommodationInfo savedAccommodation = accommodationService.save(newAccommodation);

        savedAccommodation.changeTitle(updateTitle);
        em.flush();
        em.clear();
        AccommodationInfo merged = accommodationService.findByTitle(updateTitle);
        log.info("업데이트된 숙소 이름: {}", merged.getTitle());
        Assertions.assertThat(merged.getTitle()).isEqualTo(updateTitle);


    }
}