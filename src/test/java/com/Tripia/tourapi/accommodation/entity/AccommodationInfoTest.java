package com.Tripia.tourapi.accommodation.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class AccommodationInfoTest {


    @DisplayName("AccommodationInfo 생성자 테스트")
    @Test
    void testAccommodationInfo() {
        AccommodationInfo accommodationInfo =
                new AccommodationInfo(null,"contentId","title",
                        "code","addr1","addr2",
                        "image","longitude","latitude","tel");
        log.info(accommodationInfo.toString());
    }

}