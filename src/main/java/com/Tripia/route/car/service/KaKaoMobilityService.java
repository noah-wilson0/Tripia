package com.Tripia.route.car.service;

import com.Tripia.route.car.dto.KaKaoMobilityRouteDto;
import com.Tripia.route.car.external.client.KaKaoMobilityWebclient;
import com.Tripia.route.car.external.dto.KaKaoMobilityResponse;
import com.Tripia.route.utils.KaKaoCoordinateFormatter;
import com.Tripia.tourapi.place.entity.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KaKaoMobilityService {

    private final KaKaoMobilityWebclient kaKaoMobilityWebclient;

    public KaKaoMobilityRouteDto requestRouteWithKaKaoCoordinates(Place originPlace, Place destinationPlace) {

        KaKaoCoordinateFormatter kaKaoCoordinateFormatter = new KaKaoCoordinateFormatter();
        String origin = kaKaoCoordinateFormatter.format(originPlace.getLongitude(), originPlace.getLatitude(), originPlace.getTitle());
        String destination = kaKaoCoordinateFormatter.format(destinationPlace.getLongitude(), destinationPlace.getLatitude(), destinationPlace.getTitle());

        try {
            KaKaoMobilityResponse kaKaoMobilityResponse = kaKaoMobilityWebclient.fetchRouteFromApi(origin, destination);
            if (kaKaoMobilityResponse.getRoutes() != null && !kaKaoMobilityResponse.getRoutes().isEmpty()) {
                KaKaoMobilityResponse.Route route = kaKaoMobilityResponse.getRoutes().get(0);
                if (route.getResult_code() == 0) {
                    KaKaoMobilityResponse.Summary summary = route.getSummary();
                    return KaKaoMobilityRouteDto.builder()
                            .originId(originPlace.getId())
                            .destinationId(destinationPlace.getId())
                            .taxiFare(summary.getFare().getTaxi())
                            .distance(summary.getDistance())
                            .duration(summary.getDuration()).build();
                } else {
                    throw new RuntimeException("카카오 경로 요청 실패");
                }
            } else {
                throw new RuntimeException("카카오 API 응답에 routes가 비어 있습니다.");
            }
        }catch (RuntimeException e) {
            throw new RuntimeException("카카오 경로 요청 중 오류 발생", e);
        }

    }




}
