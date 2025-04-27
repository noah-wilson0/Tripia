package com.Tripia.tourapi.scheduler.location.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class CityCodeSyncServiceTest {
    @Autowired
    private CityCodeSyncService cityCodeSyncService;
    @Test
    void syncAreaCodes() {
        cityCodeSyncService.syncAreaCodes();
    }


}