package com.Tripia.tourapi.scheduler.location.service;

import com.Tripia.tourapi.location.entity.AreaCode;


import com.Tripia.tourapi.location.service.AreaCodeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@SpringBootTest
@Transactional
class AreaCodeSyncServiceTest {
    @Autowired
    private AreaCodeSyncService areaCodeSyncService;
    @Autowired
    private AreaCodeService areaCodeService;



    @Test
    @DisplayName("지역 코드 API 동기화 후 DB 저장 여부 확인")
    void syncAreaCodes(){
        areaCodeSyncService.syncAreaCodes();
        List<AreaCode> result = areaCodeService.findAll();
        assertFalse(result.isEmpty(),"DB에 지역 코드가 저장되지 않았습니다.");
    }




}