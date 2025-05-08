package com.Tripia.route.service;

import com.Tripia.route.entity.TravelPlan;
import com.Tripia.route.entity.TravelPlanDay;
import com.Tripia.route.entity.TravelPlanPlace;
import com.Tripia.tourapi.place.entity.Place;
import com.Tripia.tourapi.place.service.PlaceService;
import com.Tripia.user.entity.User;
import com.Tripia.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
//@Transactional
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

    @Test
    void saveUser(){
        User user = userService.save(User
                .builder()
                .email("test@test.com")
                .password("123")
                .name("Test")
                .build());

        log.info(user.toString());

    }


    @Test
    void saveTravelPlan() {
        Optional<User> user = userService.findById(2L);
        travelPlanService.save(TravelPlan.builder()
                        .userId(user.get())
                        .title("첫번쪠 여행")
                        .startDate(LocalDate.of(2025,5,10))
                        .endDate(LocalDate.of(2025,5,12))
                .build());

    }

    /**
     * TravelPlanDay마다 시간당 계획이 있으므로 ERD 수정해야됨
     */
    @Test
    void saveTravelPlanDay() {
        Optional<TravelPlan> travelPlan = travelPlanService.findById(1L);

        travelPlanDayService.save(TravelPlanDay
                .builder()
                        .travelPlan(travelPlan.get())
                        .sequence(1)
                        .date(LocalDate.of(2025,5,10))

                .build());
    }

    @Test
    void saveTravelPlanPlace() {

        Optional<TravelPlanDay> travelPlanDay = travelPlanDayService.findById(1L);
        Place place = placeService.findByTitle("가계해수욕장");

        travelPlanPlaceService.save(TravelPlanPlace.builder()
                        .travelPlanDay(travelPlanDay.get())
                        .place(place)
                        .sequence(1)
                .build());



    }
}