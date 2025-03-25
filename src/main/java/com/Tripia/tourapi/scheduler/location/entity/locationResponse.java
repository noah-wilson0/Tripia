package com.Tripia.tourapi.scheduler.location.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class locationResponse {
    private Response response;

    @Getter @NoArgsConstructor
    public static class Response {
        private Body body;
    }

    @Getter
    @NoArgsConstructor
    public static class Body {
        private Items items;
    }

    @Getter @NoArgsConstructor
    public static class Items {
        private List<AreaCodeItem> item;
    }

    @Getter @NoArgsConstructor
    public static class AreaCodeItem {
        private String code;
        private String name;
    }
}
