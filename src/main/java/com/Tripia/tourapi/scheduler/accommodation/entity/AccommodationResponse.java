package com.Tripia.tourapi.scheduler.accommodation.entity;

import lombok.Getter;

import java.util.List;

@Getter

public class AccommodationResponse {

    private Response response;

    @Getter
    public static class Response{
        private Body body;
    }
    @Getter
    public static class Body{
        private Items items;
    }
    @Getter
    public static class Items{
        private List<AccommodationItem> item;
    }
    @Getter
    public static class AccommodationItem{

        private String contentid;

        private String title;

        private String areacode;

        private String addr1;
        private String addr2;

        private String firstimage;

        private String mapx; //경도
        private String mapy; //위도

        private String tel;

    }
}
