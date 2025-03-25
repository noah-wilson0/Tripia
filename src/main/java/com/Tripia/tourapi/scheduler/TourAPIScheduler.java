package com.Tripia.tourapi.scheduler;

import com.Tripia.tourapi.scheduler.location.service.AreaCodeSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * accommodation,touristSpot 스케듈러 완성하기
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class TourAPIScheduler {

    private final AreaCodeSyncService areaCodeSyncService;

    @Scheduled(cron = "0 0 0 * * *")
    public void syncAreaCodes() {
        log.info("[스케줄러] 지역 코드 동기화 시작");
        areaCodeSyncService.syncAreaCodes();
        log.info("[스케줄러] 지역 코드 동기화 완료");
    }

}
