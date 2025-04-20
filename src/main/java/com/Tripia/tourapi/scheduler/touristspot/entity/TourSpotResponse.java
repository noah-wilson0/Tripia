package com.Tripia.tourapi.scheduler.touristspot.entity;


import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class TourSpotResponse {

    private Response response;
    @Getter
    public static class Response {
        private Body body;
    }

    @Getter
    public static class Body{
        private Items items;
    }

    @Getter
    public static class Items{
        private List<TourSpotItem> item;
    }

    @Getter
    @ToString
    public static class TourSpotItem{
        private String addr1;
        private String addr2;
        private String areacode;
        private String contentid;
        private String firstimage;
        private String mapx; //경도
        private String mapy; //위도
        private String tel;
        private String title;

        public void changeTel(String tel) {
            this.tel = tel;
        }

    }
}
