package com.Tripia.tourapi.scheduler.touristspot.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class TourSpotSyncServiceTest {

    @Autowired
    private TourSpotSyncService tourSpotSyncService;

    @Test
    @DisplayName("여행장소 저장 테스트")
    void syncTourSpot() {
        tourSpotSyncService.SyncTourSpot();
    }
}