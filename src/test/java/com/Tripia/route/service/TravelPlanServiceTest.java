package com.Tripia.route.service;

import com.Tripia.route.car.dto.KaKaoMobilityRouteDto;
import com.Tripia.route.car.service.KaKaoMobilityService;
import com.Tripia.route.entity.TravelPlan;
import com.Tripia.route.entity.TravelPlanDay;
import com.Tripia.route.entity.TravelPlanPlace;
import com.Tripia.route.entity.TravelPlanRoute;
import com.Tripia.tourapi.place.entity.Place;
import com.Tripia.tourapi.place.service.PlaceService;
import com.Tripia.user.entity.User;
import com.Tripia.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;


@Slf4j
@SpringBootTest
@Transactional
class TravelPlanServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private TravelPlanService travelPlanService;
    @Autowired
    private TravelPlanDayService travelPlanDayService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private TravelPlanPlaceService travelPlanPlaceService;
    @Autowired
    private TravelPlanRouteService travelPlanRouteService;
    @Autowired
    private KaKaoMobilityService kaKaoMobilityService;


    /**
     * TODO
     * TravelPlanDay마다 시간당 계획이 있으므로 ERD 수정해야됨
     * 숙소 데이터가 모든 여행 계획에서 똑같이 쓸떄 어떻게 적용하는가?
     *
     */
    @DisplayName("초기 통합 여행 계획 테스트")
    @Test
    void integratedTravelPlanTest() {
        // 1. 유저 저장
        User user = userService.save(User.builder()
                .email("test@test.com")
                .password("123")
                .name("TestUser")
                .build());

        // 2. 여행 계획 저장
        TravelPlan travelPlan = travelPlanService.save(TravelPlan.builder()
                .userId(user)
                .title("테스트 여행")
                .startDate(LocalDate.of(2025, 5, 10))
                .endDate(LocalDate.of(2025, 5, 12))
                .build());

        // 3. 여행 날짜 저장
        TravelPlanDay travelPlanDay = travelPlanDayService.save(TravelPlanDay.builder()
                .travelPlan(travelPlan)
                .sequence(1)
                .date(LocalDate.of(2025, 5, 10))
                .build());

        // 4. 장소 선택
        Place fromPlace = placeService.findById(187265L).orElseThrow();
        Place toPlace = placeService.findById(187264L).orElseThrow();

        // 5. 장소 저장 (시간 포함)
        TravelPlanPlace place1 = travelPlanPlaceService.save(TravelPlanPlace.builder()
                .travelPlanDay(travelPlanDay)
                .place(fromPlace)
                .sequence(1)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .build());

        TravelPlanPlace place2 = travelPlanPlaceService.save(TravelPlanPlace.builder()
                .travelPlanDay(travelPlanDay)
                .place(toPlace)
                .sequence(2)
                .startTime(LocalTime.of(10, 30))
                .endTime(LocalTime.of(11, 30))
                .build());

        // 6. 경로 저장
        KaKaoMobilityRouteDto dto = kaKaoMobilityService.requestRouteWithKaKaoCoordinates(fromPlace, toPlace);

        travelPlanRouteService.save(TravelPlanRoute.builder()
                .travelPlanDay(travelPlanDay)
                .sequence(1)
                .pathType(1) // 자동차
                .fromPlace(place1)
                .toPlace(place2)
                .distance(dto.getDistance())
                .duration(dto.getDuration())
                .build());
    }


}

