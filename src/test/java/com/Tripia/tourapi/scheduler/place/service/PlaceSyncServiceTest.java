package com.Tripia.tourapi.scheduler.place.service;

import com.Tripia.tourapi.location.entity.AreaCode;
import com.Tripia.tourapi.location.service.AreaCodeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
//@Transactional
class PlaceSyncServiceTest {

    @Autowired
    private PlaceSyncService placeSyncService;

    @Autowired
    private AreaCodeService areaCodeService;

    @Test
    @DisplayName("여행지 저장 테스트")
    void syncPlaceTest() {
        placeSyncService.SyncPlace();
    }


}