package com.Tripia.route.car.service;

import com.Tripia.route.car.dto.KaKaoMobilityRouteDto;
import com.Tripia.route.dto.RouteDto;
import com.Tripia.route.service.TravelPlanRouteService;
import com.Tripia.tourapi.place.entity.Place;
import com.Tripia.tourapi.place.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class KaKaoMobilityServiceTest {

    @Autowired
    private PlaceService placeService;
    @Autowired
    private KaKaoMobilityService kaKaoMobilityService;
    Long originId=187264L;
    Long destinationId=187265L;
    @Autowired
    private TravelPlanRouteService travelPlanRouteService;


    @DisplayName("kakao mobility api 요청 확인")
    @Test
    void requestRouteWithKaKaoCoordinates() {
        Place origin = placeService.findById(originId).orElseThrow();
        log.info(origin.toString());
        Place destination = placeService.findById(destinationId).orElseThrow();
        log.info(destination.toString());
        KaKaoMobilityRouteDto dto = kaKaoMobilityService.requestRouteWithKaKaoCoordinates(origin, destination);
        log.info(dto.toString());
        RouteDto routeDto = RouteDto.builder()
                .origin(placeService.findById(dto.getOriginId()).get().getTitle())
                .destination(placeService.findById(dto.getDestinationId()).get().getTitle())
                .taxiFare(dto.getTaxiFare())
                .distance(dto.getDistance())
                .duration(dto.getDuration())
                .build();
        log.info(routeDto.toString());
    }


}