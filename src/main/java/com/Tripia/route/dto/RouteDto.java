package com.Tripia.route.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class RouteDto {
    private String origin; //출발지 이름
    private String destination; //목적지 이름
    private int taxiFare;
    private int distance; //전체 검색 결과 거리(미터)
    private int duration; //목적지까지 소요 시간(초)

    public void changeOrigin(String origin) {
        this.origin = origin;
    }
    public void changeDestination(String destination) {
        this.destination = destination;
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
        this.origin = origin;
        this.destination = destination;
        this.taxiFare = taxiFare;
        this.distance = distance;
        this.duration = duration;
    }
}
