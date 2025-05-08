package com.Tripia.route.car.external.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.RouteMatcher;

import java.util.List;

/**
 * 카카오 모빌리티 길찾기 Directions API 응답 객체.
 * 참고: https://developers.kakaomobility.com/docs/navi-api/directions/
 *
 */
@Getter
@NoArgsConstructor
public class KaKaoMobilityResponse {

    private String trans_id;
    private List<Route> routes;

    @Getter @NoArgsConstructor
    public static class Route{
        private int result_code; //경로 탐색 결과 코드
        private String result_msg; //경로 탐색 결과 메시지
        private Summary summary;
    }
    @Getter @NoArgsConstructor
    public static class Summary{
        private Origin origin; //출발지 정보
        private Destination destination; //목적지 정보
        private List<Waypoints> waypoints; //경유지 정보
        private String priority; //경로 탐색 우선순위 옵션
        private Fare fare; //요금 정보
        private int distance; //전체 검색 결과 거리(미터)
        private int duration; //목적지까지 소요 시간(초)
    }

    @Getter @NoArgsConstructor
    public static class Origin{
        private Coordinate coordinate;
    }

    @Getter @NoArgsConstructor
    public static class Destination{
        private Coordinate coordinate;
    }

    @Getter @NoArgsConstructor
    public static class Waypoints{
        private Coordinate coordinate;
    }
    @Getter @NoArgsConstructor
    public static class Coordinate{
        private String name;
        private double x;
        private double y;

    }

    @Getter @NoArgsConstructor
    public static class Fare{
        private int taxi; //택시 요금

    }




}
