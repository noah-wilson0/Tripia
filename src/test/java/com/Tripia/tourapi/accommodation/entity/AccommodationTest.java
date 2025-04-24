package com.Tripia.tourapi.accommodation.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class AccommodationTest {


    @DisplayName("AccommodationInfo 생성자 테스트")
    @Test
    void testAccommodationInfo() {
        Accommodation accommodation =
                new Accommodation(null,"contentId","title",
                        "code","addr1","addr2",
                        "image","longitude","latitude","tel");
        log.info(accommodation.toString());
    }

}