package com.Tripia.tMapPublicTransportation;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@ToString
public class tMapPublicTransPortationRequest {

    @NotNull
    private String startX;

    @NotNull
    private String startY;

    @NotNull
    private String endX;

    @NotNull
    private String endY;

    private int lang =0; // 기본값: 0 (예: 한국어)
    private String format="json"; // 기본값: json
    private int count =10; // 기본값: 10 (예: 결과 개수)

    @Builder
    public tMapPublicTransPortationRequest(String startX, String startY, String endX, String endY,
                                           int lang, String format, int count) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.lang = lang;
        this.format = format;
        this.count = count;
    }
}