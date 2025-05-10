package com.Tripia.route.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class KaKaoMobilityRouteDto {

    private Long originId;
    private Long destinationId;
    private int taxiFare;
    private int distance; //전체 검색 결과 거리(미터)
    private int duration; //목적지까지 소요 시간(초)

    public void changeOrigin(Long originId) {
        this.originId = originId;
    }
    public void changeDestination(Long destinationId) {
        this.destinationId = destinationId;
    }
    public void changeTaxiFare(int taxiFare) {
        this.taxiFare = taxiFare;
    }
    public void changeDistance(int distance) {
        this.distance = distance;
    }
    public void changeDuration(int duration) {
        this.duration = duration;
    }
    public void changeAll() {
        this.originId = originId;
        this.destinationId = destinationId;
        this.taxiFare = taxiFare;
        this.distance = distance;
        this.duration = duration;
    }
}
