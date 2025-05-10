package com.Tripia.route.controller;

import com.Tripia.route.car.dto.KaKaoMobilityRouteDto;
import com.Tripia.route.car.service.KaKaoMobilityService;
import com.Tripia.route.dto.RouteDto;
import com.Tripia.tourapi.place.entity.Place;
import com.Tripia.tourapi.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: 프론트에서 어떤 값을 받아야 될까?
 */
@RestController
@RequestMapping("/api/kakaomap")
@RequiredArgsConstructor
public class KaKaoMobilityController {

    private final PlaceService placeService;
    private final KaKaoMobilityService kaKaoMobilityService;

    @GetMapping("/route")
    public ResponseEntity<RouteDto> requestKaKaoMobility(@RequestParam Long originId, @RequestParam Long destinationId){
        Place origin = placeService.findById(originId).orElseThrow();
        Place destination = placeService.findById(destinationId).orElseThrow();
        KaKaoMobilityRouteDto dto = kaKaoMobilityService.requestRouteWithKaKaoCoordinates(origin, destination);

        return ResponseEntity.ok(RouteDto.builder()
                        .origin(placeService.findById(dto.getOriginId()).get().getTitle())
                        .destination(placeService.findById(dto.getDestinationId()).get().getTitle())
                        .taxiFare(dto.getTaxiFare())
                        .distance(dto.getDistance())
                        .duration(dto.getDuration())
                .build());
    }

}
